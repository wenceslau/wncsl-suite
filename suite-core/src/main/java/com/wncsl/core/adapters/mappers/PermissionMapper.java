package com.wncsl.core.adapters.mappers;

import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.grpc.code.ACTION;
import com.wncsl.grpc.code.PermissionGrpc;

public class PermissionMapper {

    public static Permission toEntity(PermissionModel model){
        Permission entity = new Permission(model.getUuid(), model.getRole(), model.getDescription());
        return entity;
    }

    public static PermissionModel toModel(Permission entity){
        return PermissionModel.builder()
                .uuid(entity.getUuid())
                .role(entity.getRole())
                .description(entity.getDescription())
                .build();
    }

    public static PermissionDTO toDto(Permission entity){
        return PermissionDTO.builder()
                .uuid(entity.getUuid())
                .role(entity.getRole())
                .description(entity.getDescription())
                .build();
    }

    public static PermissionGrpc toGrpc(PermissionModel model, ACTION action){
        return PermissionGrpc.newBuilder()
                .setUuid(String.valueOf(model.getUuid()))
                .setRole(model.getRole())
                .setDescription(model.getDescription())
                .setAction(action)
                .build();
    }

}
