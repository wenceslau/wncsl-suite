package com.wncsl.core.presentation.account.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDTO {

    private UUID uuid;
    private String name;
    private String username;
    private String password;
    private List<PermissionDTO> permissions = new ArrayList<>();

//    public User toEntity(){
//        return User.fromMDto(this);
//    }

//    public UserModel toModel(){
//        PermissionModel permissionSet = permissions.stream().map(p-> p.toModel()).collect(Collectors.toSet()
//        return UserModel.builder()
//                .uuid(uuid)
//                .name(name)
//                .username(name)
//                .password(password)
//                .permissions())
//                .build();
//    }

}
