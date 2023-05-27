package com.wncsl.core.domain.account.ports;

import com.wncsl.core.domain.InterfacePersistence;
import com.wncsl.core.domain.account.entity.Permission;

import java.util.UUID;

public interface PermissionPersistencePort extends InterfacePersistence<Permission> {

    boolean existByUuid(UUID uuid);

    boolean existByRole(String role);

    boolean existByRoleAndNotEqualsUuid(String role, UUID uuid);

}
