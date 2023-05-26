package com.wncsl.core.infra.config;

import com.wncsl.core.domain.account.repository.UserRepository;
import com.wncsl.core.domain.account.repository.PermissionRepository;
import com.wncsl.core.infra.domain.account.repository.UserJpaRepository;
import com.wncsl.core.infra.domain.account.repository.UserRepositoryImpl;
import com.wncsl.core.infra.domain.account.repository.PermissionJpaRepository;
import com.wncsl.core.infra.domain.account.repository.PermissionRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {

    @Bean
    public UserRepository createUserRepository(UserJpaRepository userJpaRepository){
        return new UserRepositoryImpl(userJpaRepository);
    }

    @Bean
    public PermissionRepository createPermissionRepository(PermissionJpaRepository permissionJpaRepository){
        return new PermissionRepositoryImpl(permissionJpaRepository);
    }
}
