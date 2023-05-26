package com.wncsl.core.domain.account.repository;

import com.wncsl.core.domain._shared.InterfaceRepository;
import com.wncsl.core.domain.account.entity.Permission;

import java.util.UUID;

public interface PermissionRepository extends InterfaceRepository<Permission> {

    boolean existByRole(String role);

    boolean existByRoleAndNotEqualsUuid(String role, UUID uuid);

}
