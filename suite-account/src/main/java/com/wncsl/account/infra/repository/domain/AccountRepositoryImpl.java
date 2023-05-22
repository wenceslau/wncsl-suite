package com.wncsl.account.infra.repository.domain;

import com.wncsl.account.domain.entity.Account;
import com.wncsl.account.domain.repository.AccountRepository;
import com.wncsl.account.infra.repository.db.AccountJpaRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class AccountRepositoryImpl implements AccountRepository {

    private AccountJpaRepository accountJpaRepository;
    public AccountRepositoryImpl(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }


    @Override
    public UUID create(Account entity) {
        return accountJpaRepository.save(entity.toModel()).getId();
    }

    @Override
    public UUID update(Account entity) {
        return accountJpaRepository.save(entity.toModel()).getId();
    }

    @Override
    public Account find(UUID id) {
        return accountJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found!"))
                .toEntity();
    }

    @Override
    public Set<Account> findAll() {
        return accountJpaRepository.findAll()
                .stream()
                .map(a-> a.toEntity())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existByUsername(String username) {
        return accountJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existByUsernameAndNotEqualsId(String username, UUID id) {
        return false;
    }
}
