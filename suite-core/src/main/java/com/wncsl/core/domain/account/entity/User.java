package com.wncsl.core.domain.account.entity;

import com.wncsl.core.presentation.account.dto.UserDTO;
import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.infra.domain.account.model.UserModel;

import java.util.List;
import java.util.UUID;

public class User {

    private UUID uuid;
    private String name;
    private String username;
    private String password;

    private List<Permission> permissions;

    private User(){

    }

    public User(UUID uuid, String name, String username, List<Permission> permissions) {
        this.uuid = uuid;
        this.name = name;
        this.username = username;
        this.permissions = permissions;
        validate();
    }

    public User(String name, String username,List<Permission> permissions) {
        this(UUID.randomUUID(), name, username, permissions);
    }

    public void createPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (comparePassword(oldPassword)== false){
            throw new BusinessException("The password does not match");
        }
        validatePassword(newPassword);
        if (this.password.equals(newPassword)){
            throw new BusinessException("The new password cannot be equals the previous");
        }
        this.password = password;
    }

    public boolean comparePassword(String password){
        if (password == null){
            return  false;
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
    //endregion

    //region Validates
    public void validate(){
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

    //region Factory
    public UserDTO toDTO(){
        return UserDTO.builder()
                .uuid(uuid)
                .name(name)
                .username(username)
                .password(password)
                .build();
    }

    public UserModel toModel(){
        return UserModel.builder()
                .uuid(uuid)
                .name(name)
                .username(username)
                .password(password)
                .build();
    }

//    public static User fromModel(UserModel userModel) {
//        List<Permission> list = userModel.getPermissions().stream().map(p-> p.toEntity()).collect(Collectors.toList());
//        User user = new User(userModel.getName(), userModel.getUsername(), list);
//        user.uuid = userModel.getUuid();
//        user.password = userModel.getPassword();
//        return user;
//    }

    //endregion
}
