package com.wncsl.account.domain.account.repository;

import com.wncsl.account.domain._shared.InterfaceRepository;
import com.wncsl.account.domain.account.entity.Account;

import java.util.UUID;

public interface AccountRepository extends InterfaceRepository<Account> {

    boolean existByUsername(String username);

    boolean existByUsernameAndNotEqualsId(String username, UUID id);

}
