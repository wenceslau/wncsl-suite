package com.wncsl.core.domain.account.ports;

import com.wncsl.core.domain.InterfacePersistence;
import com.wncsl.core.domain.account.entity.User;

import java.util.UUID;

public interface UserPersistencePort extends InterfacePersistence<User> {

    boolean existByUsername(String username);

    boolean existByUsernameAndNotEqualsId(String username, UUID id);

    User findByUsername(String username);
}
