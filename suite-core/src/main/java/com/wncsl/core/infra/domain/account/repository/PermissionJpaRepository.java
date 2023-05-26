package com.wncsl.core.infra.domain.account.repository;

import com.wncsl.core.infra.domain.account.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionModel, Long> {

    Optional<PermissionModel> findByUuid(UUID uuid);

    boolean existsByRole(String role);

    boolean existsByRoleAndUuidIsNot(String role, UUID uuid);

}
