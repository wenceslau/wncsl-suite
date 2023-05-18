package com.suite.auth.service;


import com.suite.auth.entity.Account;
import com.suite.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account create(Account account){
        return accountRepository.save(account);
    }

    public Account getByUsername(String username){
        return accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("No found!"));
    }

    public List<Account> listAll(){
        return accountRepository.findAll();
    }

}
