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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
import com.example.friendo.Websocket.Model.Status;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/auth")
public class AccountController {

    private final JwtService jwtService;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final UserDetailsService userDetailsService;
    private SimpMessagingTemplate simpMessagingTemplate;

    public AccountController(JwtService jwtService,AccountService accountService,AccountRepository accountRepository,SimpMessagingTemplate simpMessagingTemplate,UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userDetailsService = userDetailsService;
    }
    @GetMapping("/check")
    public ResponseEntity<?> checkIfLogIn(@CookieValue(name = "JWT",required = false) String jwt){
        if(jwt == null || jwt.isBlank()){
            return ResponseEntity.badRequest().body(false);
        }else{
            if(jwtService.isTokenExpired(jwt)){
                return ResponseEntity.badRequest().body(false);
            }
            System.out.println(jwt + "here");
            //extract userdetails
            String username = jwtService.extractUsername(jwt);

            //get userdetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(jwt, userDetails)){
                return ResponseEntity.ok().body(true);
            }
            return ResponseEntity.badRequest().body(false);
        }
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
    @MessageMapping("/user.addUser")
    @SendTo("/topic/public")
    public void addUser(SimpMessageHeaderAccessor accessor){
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            Account account = accountRepository.findByUsername(username).get();
            accountService.saveUser(account);
            AccountDTO dto = new AccountDTO();
            dto.setEmail(account.getEmail());
            dto.setFirstname(account.getFirstname());
            dto.setLastname(account.getLastname());
            dto.setUsername(account.getUsername());
            dto.setStatus(account.getStatus());
            simpMessagingTemplate.convertAndSend("/topic/public", dto);
        }
    }
    @GetMapping("search")
    public ResponseEntity<?> searchUser(@RequestParam("username")String username){
        if(username.isBlank()){
            return ResponseEntity.badRequest().body("Only space");
        }
        try{
            Optional<AccountDTO> accountDTO = Optional.of(accountService.search(username));
            if(accountDTO.isPresent()){
                return ResponseEntity.ok().body(accountDTO.get());
            }
        }catch(Exception e){
            return ResponseEntity.badRequest().body("No username found");
        }
        return ResponseEntity.badRequest().body("No username found");
    }
    @MessageMapping("/user.disconnectUser")
    @SendTo("/topic/public")
    public void disconnectUsers(SimpMessageHeaderAccessor accessor){
        String username = (String) accessor.getSessionAttributes().get("username");
        if (username != null) {
            Account account = accountRepository.findByUsername(username).get();
            accountService.disconnectUser(account);
            AccountDTO dto = new AccountDTO();
            dto.setEmail(account.getEmail());
            dto.setFirstname(account.getFirstname());
            dto.setLastname(account.getLastname());
            dto.setUsername(account.getUsername());
            dto.setStatus(account.getStatus());
            simpMessagingTemplate.convertAndSend("/topic/public", dto);
        }
    }
    public ResponseEntity<List<Account>> findConnectUsers(){
        return ResponseEntity.ok().body(accountService.findConnectedUsers());
    }
}
