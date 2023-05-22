package com.wncsl.account.domain.service;

import com.wncsl.account.domain.BusinessException;
import com.wncsl.account.domain.entity.Account;
import com.wncsl.account.domain.repository.AccountRepository;

import java.util.Set;
import java.util.UUID;

public class DomainAccountService implements InterfaceDomainService<Account> {

    private AccountRepository accountRepository;

    public DomainAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UUID create(Account account){

        validateUsername(account.getUsername());

        UUID uuid = accountRepository.create(account);

        account.defineId(uuid);

        return uuid;

    }

    public void update(Account account){

        accountRepository.update(account);

    }

    public Set<Account> fildAll() {
        return accountRepository.findAll();
    }

    public Account findById(UUID id){
        return accountRepository.find(id);
    }

    private void validateUsername(String username){

        boolean exist = accountRepository.existByUsername(username);

        if (exist){
            throw new BusinessException("This username had already exist");
        }

    }

}
