package com.example.friendo.SaveFeature.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.AccountFeature.Service.JwtService;
import com.example.friendo.SaveFeature.DTO.SaveDTO;
import com.example.friendo.SaveFeature.Model.SaveModel;
import com.example.friendo.SaveFeature.Services.SaveServices;

@RestController
@RequestMapping("api/v1/save")
public class SaveController {
    public SaveServices saveServices;
    public JwtService jwtService;
    public AccountRepository accountRepository;
    @Autowired
    public SaveController(SaveServices saveServices,JwtService jwtService,AccountRepository accountRepository){
        this.saveServices = saveServices;
        this.jwtService = jwtService;
        this.accountRepository = accountRepository;
    }
    @PostMapping("/add")
    public ResponseEntity<?> save(@CookieValue(name = "JWT", required = false) String jwt,@RequestParam("feedId")Integer feedId){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(account == null){
            return ResponseEntity.badRequest().body("NO account found");
        }
        if (jwt == null){
            return ResponseEntity.badRequest().body("Jwt is empty");
        }
        if(Optional.of(saveServices.createSave(feedId, account.getId())).isPresent()){
            return ResponseEntity.ok().body("Successfully added");
        }
        return ResponseEntity.badRequest().body("Something must have happen");
    }
    @DeleteMapping("/unsave")
    public ResponseEntity<?> unsave(@CookieValue(name = "JWT", required = false) String jwt,@RequestParam("feedId")Integer feedId){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        if(account == null){
            return ResponseEntity.badRequest().body("NO account found");
        }
        if (jwt == null){
            return ResponseEntity.badRequest().body("Jwt is empty");
        }
        saveServices.unsave(feedId, account.getId());
        return ResponseEntity.ok().body("Successfully added");
    }
    @GetMapping("/allSave")
    public ResponseEntity<?> getAllSaved(@CookieValue(name = "JWT", required = false) String jwt){
        String username = jwtService.extractUsername(jwt);
        Account account = accountRepository.findByUsername(username).get();
        System.out.println("hehehe ");
        if(account == null){
            return ResponseEntity.badRequest().body("NO account found");
        }
        if (jwt == null){
            return ResponseEntity.badRequest().body("Jwt is empty");
        }
        List<SaveDTO> saveModel = saveServices.getAllSaved(account.getId());
        if(Optional.of(saveModel).isPresent()){
            return ResponseEntity.ok().body(saveModel);
        }
        return ResponseEntity.badRequest().body("Something must have happen");
    }
}
