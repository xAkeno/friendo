package com.example.friendo.AccountExtraFeature.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.AccountExtraFeature.DTO.AccountExtraDTO;
import com.example.friendo.AccountExtraFeature.Model.AccountExtraModel;
import com.example.friendo.AccountExtraFeature.Repository.AccountExtraRepository;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;
import com.example.friendo.MicrosoftAzure.imageMetaDataService;

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

    public AccountExtraModel registerExtra(AccountExtraModel accountExtraModel,MultipartFile img ,Integer id){
        if(accountExtraModel == null){
            throw new RuntimeException("Extra is Empty");
        }
        if(id == null){
            throw new RuntimeException("No Jwt found");
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No account found with that id"));

        Account accountDto = new Account();
        accountDto.setId(account.getId());
        accountDto.setFirstname(account.getFirstname());
        accountDto.setLastname(account.getLastname());
        accountDto.setUsername(account.getUsername());
        accountDto.setEmail(account.getEmail());
        accountDto.setAge(account.getAge());
        accountDto.setGender(account.getGender());

        AccountExtraModel added = new AccountExtraModel();
        added.setBio(accountExtraModel.getBio());
        added.setCity(accountExtraModel.getCity());
        added.setCountry(accountExtraModel.getCountry());
        added.setSchool(accountExtraModel.getSchool());
        added.setStatus(accountExtraModel.getStatus());
        added.setAccount(accountDto);
        added.setProfileImg(imageMetaDataServices.uploadProfileImg(img));
        return accountExtraRepository.save(added);
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

        return accountExtraRepository.findByAccount(id).get();
    }
}
