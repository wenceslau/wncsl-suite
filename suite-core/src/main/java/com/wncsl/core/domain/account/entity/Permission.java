package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.infra.domain.account.model.PermissionModel;
import com.wncsl.core.presentation.account.dto.PermissionDTO;

import java.util.UUID;

public class Permission {

    private UUID uuid;

    private String role;

    private String description;

    public Permission(String role, String description) {
        validadeDescription(description);
        this.uuid = UUID.randomUUID();
        this.role = role;
        this.description = description;
    }

    public void changeDescription(String description){
        validadeDescription(description);
        this.description = description;
    }

    public void changeRole(String role){
        validadeRole(role);
        this.role = role;
    }


    public UUID getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }


    //region Validade
    private void validadeRole(String role) {
        if (role == null || role.isBlank()){
            throw new BusinessException("Role is required");
        }
    }

    private void validadeDescription(String description) {
        if (description == null || description.isBlank()){
            throw new BusinessException("Description is required");
        }
    }
    //endregion

    //region Factory
    public PermissionDTO toDTO(){
        return PermissionDTO.builder()
                .uuid(uuid)
                .role(role)
                .description(description)
                .build();
    }
    public static Permission fromMDto(PermissionDTO permissionDTO) {
        Permission permission = new Permission(permissionDTO.getRole(), permissionDTO.getDescription());
        permission.uuid = permissionDTO.getUuid();
        return permission;
    }

    public PermissionModel toModel(){
        return PermissionModel.builder()
                .uuid(uuid)
                .role(role)
                .description(description)
                .build();
    }

    public static Permission fromModel(PermissionModel permissionModel) {
        Permission permission = new Permission(permissionModel.getRole(), permissionModel.getDescription());
        permission.uuid = permissionModel.getUuid();
        return permission;
    }


    //endregion
}
