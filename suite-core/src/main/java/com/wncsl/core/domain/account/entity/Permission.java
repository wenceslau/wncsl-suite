package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;

import java.util.UUID;

public class Permission {

    private UUID uuid;

    private String role;

    private String description;

    public Permission(UUID uuid, String role, String description) {
        this.uuid = uuid;
        this.role = role;
        this.description = description;
        validadeDescription(description);
        generateUuid();
    }

    /**
     * Constructor using only in domain package
     */
    protected Permission(UUID uuid){
        this.uuid = uuid;
    }

    public void changeDescription(String description){
        validadeDescription(description);
        this.description = description;
    }

    public void changeRole(String role){
        validadeRole(role);
        this.role = role;
    }

    //region Getters
    public UUID getUuid() {
        return uuid;
    }

    public String getRole() {
        return role;
    }

    public String getDescription() {
        return description;
    }
    //endregion

    //region Validade
    private void generateUuid(){
        if (uuid == null){
            uuid = UUID.randomUUID();
        }
    }
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

}
