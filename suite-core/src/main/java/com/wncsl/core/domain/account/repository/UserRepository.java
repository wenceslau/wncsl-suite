package com.wncsl.core.domain.account.repository;

import com.wncsl.core.domain._shared.InterfaceRepository;
import com.wncsl.core.domain.account.entity.User;

import java.util.UUID;

public interface UserRepository extends InterfaceRepository<User> {

    boolean existByUsername(String username);

    boolean existByUsernameAndNotEqualsId(String username, UUID id);

}
