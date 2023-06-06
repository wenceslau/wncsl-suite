package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.ports.PermissionDomainServicePort;
import com.wncsl.core.domain.account.ports.PermissionPersistencePort;

import java.util.Set;
import java.util.UUID;

public class PermissionDomainServiceImpl implements PermissionDomainServicePort {

    private PermissionPersistencePort permissionPersistencePort;

    public PermissionDomainServiceImpl(PermissionPersistencePort permissionPersistencePort) {
        this.permissionPersistencePort = permissionPersistencePort;
    }

    public UUID create(Permission permission){

        validateRole(permission.getRole());

        UUID uuid = permissionPersistencePort.create(permission);

        return uuid;
    }

    public UUID update(Permission permission){

        permissionPersistencePort.update(permission);

        return permission.getUuid();
    }

    public Permission findById(UUID id){
        return permissionPersistencePort.findById(id);
    }

    private void validateRole(String role){

        boolean exist = permissionPersistencePort.existByRole(role);

        if (exist){
            throw new BusinessException("This role had already exist");
        }

    }

    @Override
    public boolean existByUuid(UUID uuid) {
        return permissionPersistencePort.existByUuid(uuid);
    }
}
