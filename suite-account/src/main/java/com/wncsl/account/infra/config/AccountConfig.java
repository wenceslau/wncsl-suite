package com.wncsl.account.infra.config;

import com.wncsl.account.domain.account.repository.AccountRepository;
import com.wncsl.account.domain.account.repository.PermissionRepository;
import com.wncsl.account.infra.domain.account.repository.AccountJpaRepository;
import com.wncsl.account.infra.domain.account.repository.AccountRepositoryImpl;
import com.wncsl.account.infra.domain.account.repository.PermissionJpaRepository;
import com.wncsl.account.infra.domain.account.repository.PermissionRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountRepository createAccountRepository(AccountJpaRepository accountJpaRepository){
        return new AccountRepositoryImpl(accountJpaRepository);
    }

    @Bean
    public PermissionRepository createPermissionRepository(PermissionJpaRepository permissionJpaRepository){
        return new PermissionRepositoryImpl(permissionJpaRepository);
    }
}
