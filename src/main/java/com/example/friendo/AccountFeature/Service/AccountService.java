package com.example.friendo.AccountFeature.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javax.management.RuntimeErrorException;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.DTO.LoginUserDto;
import com.example.friendo.AccountFeature.DTO.RegisterUserDto;
import com.example.friendo.AccountFeature.DTO.VerifyUserDto;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Model.Role;
import com.example.friendo.AccountFeature.Repository.AccountRepository;

import jakarta.mail.MessagingException;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private emailService EmailServices;
    @Autowired
    public AccountService(AccountRepository accountRepository,PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,emailService EmailServices){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.EmailServices = EmailServices;
    }

    //LogIn
    public Account Login(LoginUserDto input){
        Account account = accountRepository.findByUsername(input.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        if(!account.isEnabled()){
            throw new RuntimeException("Account not verified. Please verify your account");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(),input.getPassword()));
        return account;
    }
    public Account Register(RegisterUserDto input){
        Account account = new Account();
        account.setFirstname(input.getFirstname());
        account.setLastname(input.getLastname());
        account.setAge(input.getAge());
        account.setGender(input.getGender());
        account.setUsername(input.getUsername());
        account.setEmail(input.getEmail());
        account.setPassword(passwordEncoder.encode(input.getPassword()));
        account.setRole(Role.USER); // Always set a default role
        account.setVerification(generateVerificationCode());
        account.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        account.setEnabled(false);
        sendVerificationEmail(account);
        return accountRepository.save(account);
    }

    private void sendVerificationEmail(Account account) {
        String subject = "Account verification code";
        String veificationCode = account.getVerification();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + veificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try{
            EmailServices.sendVerificationEmail(account.getEmail(),subject,htmlMessage);
        }catch(MessagingException e){
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
    public void verifyUser(VerifyUserDto input){
        System.out.println("emeail : " + input.getEmail());
        Optional<Account> optionalUser = accountRepository.findByEmail(input.getEmail());
        if(optionalUser.isPresent()){
            System.out.println("boom");
            Account account = optionalUser.get();
            if(account.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())){
                throw new RuntimeException("Verification is expired");
            }
            if(account.getVerification().equals(input.getVerificationCode())){
                account.setEnabled(true);
                account.setVerification(null);
                account.setVerificationCodeExpiresAt(null);
                accountRepository.save(account);
            }else{
                throw new RuntimeException("Invalid verification code");
            }
        }else{
            System.out.println("heettt");
            // throw new RuntimeException("User not found");
        }
    }
    public void resendVerificationCode(String email){
        Optional<Account> opt = accountRepository.findByEmail(email);
        System.out.println(email + "<<<<");
        if(opt.isPresent()){
            Account account = opt.get();
            if(account.isEnabled()){
                throw new RuntimeException("Account is already verified");
            }
            account.setVerification(generateVerificationCode());
            account.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(account);
            accountRepository.save(account);
        }else{
            throw new RuntimeException("User not found");
        }
    }
}

