package com.example.friendo.AccountFeature.Service;

import java.util.ArrayList;
import java.util.Optional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.friendo.AccountFeature.DTO.AccountDTO;
import com.example.friendo.AccountFeature.Model.Account;
import com.example.friendo.AccountFeature.Repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    //LogIn
    public Optional<AccountDTO> Login(Account account){
        Optional<Account> GET = Optional.of(account);
        if(GET.isPresent()){
            Optional<Account> desc = accountRepository.findByEmailAndPassword(account.getEmail(), account.getPassword());
            AccountDTO newacc = new AccountDTO();
            newacc.setEmail(desc.get().getEmail());
            newacc.setId(desc.get().getId());
            return Optional.of(newacc);
        }
        return Optional.empty();
    }
    public Optional<Account> Register(Account account){
        Optional<Account> GET = Optional.of(account);
        if(GET.isPresent()){
            return Optional.of(accountRepository.save(account));
        }
        return null;
    }
}
