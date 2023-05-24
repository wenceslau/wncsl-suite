package com.wncsl.account.domain.account.repository;

import com.wncsl.account.domain._shared.InterfaceRepository;
import com.wncsl.account.domain.account.entity.Account;
import com.wncsl.account.domain.account.entity.Permission;

import java.util.UUID;

public interface PermissionRepository extends InterfaceRepository<Permission> {

    boolean existByRole(String role);

    boolean existByRoleAndNotEqualsUuid(String role, UUID uuid);

}
