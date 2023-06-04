package com.wncsl.auth.domain.permission;


import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission create(Permission permission){

        if (permissionRepository.existsByRole(permission.getRole())){
            throw new RuntimeException("This role already exist!");
        }

        return permissionRepository.save(permission);
    }

    public Permission update(Permission permission){

        if (permissionRepository.existsByRoleAndUuidIsNot(permission.getRole(), permission.getUuid())){
            throw new RuntimeException("This role already exist!");
        }

        Permission permissiondb = permissionRepository.findById(permission.getUuid()).get();
        BeanUtils.copyProperties(permission, permissiondb, "uuid", "role");
        return permissionRepository.save(permissiondb);
    }

    public List<Permission> listAll(){
        return permissionRepository.findAll();
    }

}
