package com.wncsl.core.adapters.mappers;

import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.grpc.code.UserGrpc;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserModel model){
        User entity = new User(model.getUuid(), model.getName(), model.getUsername());
        entity.createPassword(model.getPassword());
        for (PermissionModel p : model.getPermissions()) {
            entity.addPermission(new Permission(p.getUuid(), p.getRole(), p.getDescription()));
        }
        return entity;
    }

    public static UserModel toModel(User entity) {
        Set<PermissionModel> set = entity.getPermissions().stream()
                .map(p -> PermissionMapper.toModel(p))
                .collect(Collectors.toSet());
        return UserModel.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .permissions(set)
                .build();
    }

    public static UserDTO toDto(User entity) {
        List<PermissionDTO> list = entity.getPermissions().stream()
                .map(p -> PermissionMapper.toDto(p))
                .collect(Collectors.toList());
        return UserDTO.builder()
                .uuid(entity.getUuid())
                .name(entity.getName())
                .username(entity.getUsername())
                .permissions(list)
                .build();
    }


    public static UserGrpc toGrpc(User entity){
        return UserGrpc.newBuilder()
                .setUuid(String.valueOf(entity.getUuid()))
                .setName(entity.getName())
                .setUsername(entity.getUsername())
                .setPassword(entity.getPassword())
                .build();
    }


}
