package com.wncsl.auth.domain.permission;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

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
        BeanUtils.copyProperties(permission, permissiondb);
        return permissionRepository.save(permissiondb);
    }

    public List<Permission> listAll(){
        return permissionRepository.findAll();
    }

}
