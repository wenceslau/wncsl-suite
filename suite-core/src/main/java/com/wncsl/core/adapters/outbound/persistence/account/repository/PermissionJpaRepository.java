package com.wncsl.core.adapters.outbound.persistence.account.repository;

import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionJpaRepository extends PagingAndSortingRepository<PermissionModel, UUID> {

    Optional<PermissionModel> findByUuid(UUID uuid);

    boolean existsByRole(String role);

    boolean existsByRoleAndUuidIsNot(String role, UUID uuid);

    Page<PermissionModel> findAllByRoleContainingIgnoreCase(Pageable pageable, String role);

    Page<PermissionModel> findAllByDescriptionContainingIgnoreCase(Pageable pageable, String description);

}
