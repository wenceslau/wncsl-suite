package com.wncsl.account.presentation.account.dto;

import com.wncsl.account.domain.account.entity.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PermissionDTO {

    private UUID uuid;

    private String role;

    private String description;

    public Permission toEntity(){
        return Permission.fromMDto(this);
    }

//    public AccountModel toModel(){
//        return AccountModel.builder()
//                .id(id)
//                .name(name)
//                .username(name)
//                .password(password)
//                .build();
//    }
}
