package com.wncsl.core.adapters.config;

import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.UserDomainServicePort;
import com.wncsl.core.domain.account.ports.UserPersistencePort;
import com.wncsl.core.domain.account.ports.PermissionPersistencePort;
import com.wncsl.core.adapters.outbound.persistence.account.repository.UserJpaRepository;
import com.wncsl.core.adapters.outbound.persistence.account.repository.UserPersistencePortImpl;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionPersistencePortImpl;
import com.wncsl.core.domain.account.service.PermissionDomainServiceImpl;
import com.wncsl.core.domain.account.service.UserDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserPersistencePort createUserRepository(UserJpaRepository userJpaRepository){
        return new UserPersistencePortImpl(userJpaRepository);
    }

    @Bean
    public PermissionPersistencePort createPermissionRepository(PermissionJpaRepository permissionJpaRepository){
        return new PermissionPersistencePortImpl(permissionJpaRepository);
    }

    @Bean
    public PermissionDomainServicePort createPermissionDomainServicePort(PermissionPersistencePort permissionPersistencePort){
        return new PermissionDomainServiceImpl(permissionPersistencePort);
    }

    @Bean
    public UserDomainServicePort createUserDomainServicePort(UserPersistencePort userPersistencePort, PermissionDomainServicePort permissionDomainServicePort){
        return  new UserDomainServiceImpl(userPersistencePort, permissionDomainServicePort );
    }


}
