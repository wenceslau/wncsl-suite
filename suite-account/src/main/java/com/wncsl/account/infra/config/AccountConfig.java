package com.wncsl.account.infra.config;

import com.wncsl.account.infra.domain.account.AccountJpaRepository;
import com.wncsl.account.infra.domain.account.AccountRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public AccountRepositoryImpl createAccountRepository(AccountJpaRepository accountJpaRepository){
        return new AccountRepositoryImpl(accountJpaRepository);
    }
}
