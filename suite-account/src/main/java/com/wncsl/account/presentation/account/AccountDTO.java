package com.wncsl.account.presentation.account;

import com.wncsl.account.domain.account.Account;
import com.wncsl.account.infra.domain.account.AccountModel;
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



    public Account toEntity(){
        return Account.fromMDto(this);
    }

    public AccountModel toModel(){
        return AccountModel.builder()
                .id(id)
                .name(name)
                .username(name)
                .password(password)
                .build();
    }

}
