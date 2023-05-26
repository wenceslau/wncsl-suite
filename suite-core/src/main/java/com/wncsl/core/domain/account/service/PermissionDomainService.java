package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.domain._shared.InterfaceDomainService;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.repository.PermissionRepository;

import java.util.Set;
import java.util.UUID;

public class PermissionDomainService implements InterfaceDomainService<Permission> {

    private PermissionRepository permissionRepository;


    public PermissionDomainService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public UUID create(Permission permission){

        validateRole(permission.getRole());

        UUID uuid = permissionRepository.create(permission);

        return uuid;
    }

    public void update(Permission permission){

        permissionRepository.update(permission);

    }

    public Set<Permission> fildAll() {
        return permissionRepository.findAll();
    }

    public Permission findById(UUID id){
        return permissionRepository.find(id);
    }

    private void validateRole(String role){

        boolean exist = permissionRepository.existByRole(role);

        if (exist){
            throw new BusinessException("This role had already exist");
        }

    }

}
