package com.wncsl.account.domain.entity;

import com.wncsl.account.presentation.application.AccountDTO;
import com.wncsl.account.domain.BusinessException;
import com.wncsl.account.infra.model.AccountModel;

import java.util.Random;
import java.util.UUID;

public class Account {

    private UUID id;
    private String name;
    private String username;
    private String password;

    public Account(String name, String username) {
        this.name = name;
        this.username = username;
        createPassword(new Random().toString());
        validate();
    }

    public Account(String name, String username, String password) {
        this(name, username);
        createPassword(password);
    }

    public Account(UUID id, String name, String username, String password) {
        this(name, username, password);
        this.id = id;
        //validateId();
    }

    public void defineId(UUID id){
        validateId(id);
        this.id = id;
    }

    public void createPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (comparePassword(oldPassword)== false){
            throw new RuntimeException("The password does not match");
        }
        validatePassword(newPassword);
        if (this.password.equals(newPassword)){
            throw new RuntimeException("The new password cannot be equals the previous");
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
        return id;
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

    private void validateId(UUID id) {
        if (id == null) {
            throw new BusinessException("Id is required");
        }
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

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new BusinessException("Password is required");
        }

        if (password.length() < 3) {
            throw new BusinessException("Password require at least 3 character");
        }
    }
    //endregion

    public AccountDTO toDTO(){
        return AccountDTO.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .build();
    }

    public AccountModel toModel(){
        return AccountModel.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .build();
    }
}
