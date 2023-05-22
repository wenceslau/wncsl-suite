package com.wncsl.account.domain.repository;

import com.wncsl.account.domain.entity.Account;

import java.util.UUID;

public interface AccountRepository extends InterfaceRepository<Account> {

    boolean existByUsername(String username);

    boolean existByUsernameAndNotEqualsId(String username, UUID id);

}
