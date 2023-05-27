package com.wncsl.core.domain.account.entity;

import com.wncsl.core.domain.BusinessException;

import java.util.*;

public class User {

    private UUID uuid;
    private String name;
    private String username;
    private String password;
    private List<Permission> permissions = new ArrayList<>();

    public User(UUID uuid, String name, String username) {
        this.uuid = uuid;
        this.name = name;
        this.username = username;
        this.permissions = permissions;
        generateUuid();
        validate();
    }

    public void addPermission(Permission permission){
        permissions.add(permission);
    }

    public void addPermissionUuid(UUID uuidPermission){
        permissions.add(new Permission(uuidPermission));
    }

    public void createPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void changePassword(String oldPassword, String newPassword) {
        System.out.println(password);
        System.out.println(oldPassword);
        System.out.println(newPassword);
        if (comparePassword(oldPassword) == false) {
            throw new BusinessException("The password does not match");
        }
        validatePassword(newPassword);
        if (this.password.equals(newPassword)) {
            throw new BusinessException("The new password cannot be equals the previous");
        }
        this.password = password;
    }

    public boolean comparePassword(String password) {
        if (password == null) {
            return false;
        }
        return this.password.equals(password);
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    //region Getter
    public UUID getId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPassword() {
        return password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    //endregion

    //region Validates
    private void generateUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }

    public void validate() {
        validateName(this.name);
        validateUsername(this.username);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("Name is required");
        }
    }

    private void validateUsername(String username) {
        //TODO Identify in this design how to check if the username had already in database
        if (username == null || username.isBlank()) {
            throw new BusinessException("Username is required");
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new BusinessException("Password is required");
        }

        if (password.length() < 3) {
            throw new BusinessException("Password require at least 3 character");
        }
    }
    //endregion
}

