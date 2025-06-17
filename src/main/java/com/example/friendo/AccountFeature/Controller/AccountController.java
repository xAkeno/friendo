package com.example.friendo.AccountFeature.Controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.DTO.AccountProfileDTO;
import com.example.friendo.AccountFeature.DTO.LoginUserDto;
import com.example.friendo.AccountFeature.DTO.RegisterUserDto;
import com.example.friendo.AccountFeature.DTO.VerifyUserDto;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.AccountService;
import com.example.friendo.AccountFeature.Service.JwtService;
import com.example.friendo.AccountFeature.responses.LoginResponses;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AccountController {

    private final JwtService jwtService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(JwtService jwtService,AccountService accountService,AccountRepository accountRepository){
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody RegisterUserDto registerUserDto){
        Account reg = accountService.Register(registerUserDto);
        return ResponseEntity.ok(reg); 
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponses> login(@RequestBody LoginUserDto loginUserDto,HttpServletResponse response){
        Account reg = accountService.Login(loginUserDto);
        String jwt = jwtService.generateToken(reg);
        LoginResponses loginResponses = new LoginResponses(jwt, jwtService.getJwtExpiration());
        Cookie cookie = new Cookie("JWT", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true if using HTTPS
        cookie.setMaxAge((int) Duration.ofDays(1).getSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(loginResponses);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto verifyUserDto){
        try{
            System.out.println(verifyUserDto.getEmail() + verifyUserDto.getVerificationCode() + "<==============");
            accountService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Account verify successfully");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendCode(@RequestBody VerifyUserDto email){
        try{
            accountService.resendVerificationCode(email.getEmail());
            return ResponseEntity.ok().body("Verification send again");
        }catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<AccountProfileDTO> findUserProfile(@CookieValue(name = "JWT", required = false) String jwt,@RequestParam(value = "username",required = false)String user){
        if (jwt == null) {
            return ResponseEntity.badRequest().body(null);
        }

        String jwtUsername = jwtService.extractUsername(jwt);
        Optional<Account> jwtUserOpt = accountRepository.findByUsername(jwtUsername);
        if (jwtUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Account jwtUser = jwtUserOpt.get();

        Integer profileId;
        boolean isOwner;

        if (user != null && !user.isBlank()) {
            Optional<Account> profileUserOpt = accountRepository.findByUsername(user);
            if (profileUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            Account profileUser = profileUserOpt.get();
            profileId = profileUser.getId();
            isOwner = jwtUser.getId().equals(profileUser.getId());
        } else {
            profileId = jwtUser.getId();
            isOwner = true;
        }

        AccountProfileDTO dto = accountService.getUserOnProfile(profileId, isOwner);
        return ResponseEntity.ok().body(dto);
    }
}
