package com.example.friendo.AccountExtraFeature.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountExtraFeature.DTO.AccountExtraDTO;
import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;
import com.example.friendo.AccountExtraFeature.Model.Status;
import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;

import jakarta.transaction.Transactional;

@Service
public class AccountExtraService {
    private AccountExtraRepository accountExtraRepository;
    private imageMetaDataService imageMetaDataServices;
    private AccountRepository accountRepository;
    @Autowired
    public AccountExtraService(AccountExtraRepository accountExtraRepository,imageMetaDataService imageMetaDataServices,AccountRepository accountRepository){
        this.accountExtraRepository = accountExtraRepository;
        this.imageMetaDataServices = imageMetaDataServices;
        this.accountRepository = accountRepository;
    }
    @Transactional
    public AccountExtraModel registerExtra(AccountExtraModel accountExtraModel, MultipartFile img, Integer id) {
        
        if (accountExtraModel == null) {
            throw new RuntimeException("Extra is Empty");
        }

        if (id == null) {
            throw new RuntimeException("No JWT found");
        }

        Account account = accountRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("No account found with that id"));

        Optional<AccountExtraModel> optionalExtra = accountExtraRepository.findByAccount(account.getId());

        AccountExtraModel extra;

        if (optionalExtra.isPresent()) {
            // Update existing
            extra = optionalExtra.get();
        } else {
            // Create new
            extra = new AccountExtraModel();
            extra.setAccount(account); // ✅ use real account here
        }

        // Update only if fields are present and non-blank
        if (accountExtraModel.getBio() != null && !accountExtraModel.getBio().isBlank()) {
            extra.setBio(accountExtraModel.getBio());
        }
        if (accountExtraModel.getCity() != null && !accountExtraModel.getCity().isBlank()) {
            extra.setCity(accountExtraModel.getCity());
        }
        if (accountExtraModel.getCountry() != null && !accountExtraModel.getCountry().isBlank()) {
            extra.setCountry(accountExtraModel.getCountry());
        }
        if (accountExtraModel.getSchool() != null && !accountExtraModel.getSchool().isBlank()) {
            extra.setSchool(accountExtraModel.getSchool());
        }
        if (accountExtraModel.getStatus() != null) {
            extra.setStatus(Status.fromTo(String.valueOf(accountExtraModel.getStatus())));
        }

        // Image update
        if (img != null && !img.isEmpty()) {
            extra.setProfileImg(imageMetaDataServices.uploadProfileImg(img));
        }
        
        return accountExtraRepository.save(extra);
    }

    public AccountExtraModel getExtra(Integer id){
        if(id == null){
            throw new RuntimeException("No id found");
        }
        // AccountExtraModel acc = accountExtraRepository.findByAccount(id).orElseThrow(() -> new NoSuchElementException("No account found with the id of : "+id));
        // AccountExtraDTO dto = new AccountExtraDTO();
        // dto.setId(acc.getId());
        // dto.setBio(acc.getBio());
        // dto.setCity(acc.getCity());
        // dto.setCountry(acc.getCountry());
        // dto.setProfileImg(acc.getProfileImg());
        // dto.setSchool(acc.getSchool());
        // dto.setStatus(acc.getStatus());
        // dto.setAccount(acc.getAccount());
        AccountExtraModel accountExtraModel = accountExtraRepository.findByAccount(id).get();
        // System.out.println(accountExtraModel.getCountry() + " dito" + accountExtraModel.getStatus());
        return accountExtraModel;
    }
}
