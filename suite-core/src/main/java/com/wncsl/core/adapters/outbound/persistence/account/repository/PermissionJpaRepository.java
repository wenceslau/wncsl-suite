package com.wncsl.core.adapters.outbound.persistence.account.repository;

import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionModel, UUID> {

    Optional<PermissionModel> findByUuid(UUID uuid);

    boolean existsByRole(String role);

    boolean existsByRoleAndUuidIsNot(String role, UUID uuid);

}
