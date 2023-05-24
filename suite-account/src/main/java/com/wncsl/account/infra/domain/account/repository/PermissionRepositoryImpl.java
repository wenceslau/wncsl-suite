package com.wncsl.account.infra.domain.account.repository;

import com.wncsl.account.domain.account.entity.Account;
import com.wncsl.account.domain.account.entity.Permission;
import com.wncsl.account.domain.account.repository.AccountRepository;
import com.wncsl.account.domain.account.repository.PermissionRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class PermissionRepositoryImpl implements PermissionRepository {

    private PermissionJpaRepository permissionJpaRepository;
    public PermissionRepositoryImpl(PermissionJpaRepository permissionJpaRepository) {
        this.permissionJpaRepository = permissionJpaRepository;
    }


    @Override
    public UUID create(Permission entity) {
        return permissionJpaRepository.save(entity.toModel()).getUuid();
    }

    @Override
    public UUID update(Permission entity) {
        return permissionJpaRepository.save(entity.toModel()).getUuid();
    }

    @Override
    public Permission find(UUID id) {
        return permissionJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found!"))
                .toEntity();
    }

    @Override
    public Set<Permission> findAll() {
        return permissionJpaRepository.findAll()
                .stream()
                .map(a-> a.toEntity())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existByRole (String role) {
        return permissionJpaRepository.existsByRole(role);
    }

    @Override
    public boolean existByRoleAndNotEqualsUuid(String role, UUID uuid) {
        return permissionJpaRepository.existsByRoleAndUuidIsNot(role, uuid);
    }


}
