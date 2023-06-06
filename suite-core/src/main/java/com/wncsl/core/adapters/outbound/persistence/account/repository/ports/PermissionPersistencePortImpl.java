package com.wncsl.core.adapters.outbound.persistence.account.repository.ports;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.ports.PermissionPersistencePort;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class PermissionPersistencePortImpl implements PermissionPersistencePort {

    private PermissionJpaRepository permissionJpaRepository;
    public PermissionPersistencePortImpl(PermissionJpaRepository permissionJpaRepository) {
        this.permissionJpaRepository = permissionJpaRepository;
    }

    @Override
    public UUID create(Permission entity) {
        return permissionJpaRepository.save(PermissionMapper.toModel(entity)).getUuid();
    }

    @Override
    public UUID update(Permission entity) {
        return permissionJpaRepository.save(PermissionMapper.toModel(entity)).getUuid();
    }

    @Override
    public Permission findById(UUID id) {
        PermissionModel model = permissionJpaRepository.findByUuid(id)
                .orElseThrow(() -> new RuntimeException("Not found!"));

        return PermissionMapper.toEntity(model);
    }

    @Override
    public boolean existByUuid(UUID uuid) {
        return permissionJpaRepository.existsById(uuid);
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
