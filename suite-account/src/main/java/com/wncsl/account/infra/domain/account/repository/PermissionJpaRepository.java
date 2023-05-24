package com.wncsl.account.infra.domain.account.repository;

import com.wncsl.account.infra.domain.account.model.AccountModel;
import com.wncsl.account.infra.domain.account.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionModel, UUID> {

    boolean existsByRole(String role);

    boolean existsByRoleAndUuidIsNot(String role, UUID uuid);

}
