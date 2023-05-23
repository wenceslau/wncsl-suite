package com.wncsl.account.domain.account;

import com.wncsl.account.domain._shared.InterfaceRepository;

import java.util.UUID;

public interface AccountRepository extends InterfaceRepository<Account> {

    boolean existByUsername(String username);

    boolean existByUsernameAndNotEqualsId(String username, UUID id);

}
