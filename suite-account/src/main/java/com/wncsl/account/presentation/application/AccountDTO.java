package com.wncsl.account.presentation.application;

import com.wncsl.account.domain.entity.Account;
import com.wncsl.account.infra.model.AccountModel;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountDTO {

    private UUID id;
    private String name;
    private String username;
    private String password;

    public AccountModel toModel(){
        return AccountModel.builder()
                .id(id)
                .name(name)
                .username(name)
                .password(password)
                .build();
    }

    public Account toEntity(){
        return new Account(id, name, username, password);
    }

}
