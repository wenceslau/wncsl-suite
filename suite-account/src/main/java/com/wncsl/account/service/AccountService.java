package com.wncsl.account.service;


import com.wncsl.account.entity.Account;
import com.wncsl.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account create(Account account){
        account = accountRepository.save(account);
        //AccountClientGrpc accountClientGrpc = new AccountClientGrpc("localhost", 9090);
        //accountClientGrpc.createAccount(account);
        return account;
    }

    public Account getById(Long id){
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("No found!"));
    }

    public List<Account> listAll(){
        return accountRepository.findAll();
    }

}
