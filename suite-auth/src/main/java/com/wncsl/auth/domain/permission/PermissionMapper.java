package com.wncsl.auth.domain.permission;

import com.wncsl.grpc.account.PermissionGrpc;

import java.util.UUID;

public class PermissionMapper {

    public static Permission build(PermissionGrpc grpc){
        return Permission.builder()
                .uuid(UUID.fromString(grpc.getUuid()))
                .role(grpc.getRole())
                .description(grpc.getDescription())
                .build();
    }

    public static PermissionGrpc build(Permission model){
        return PermissionGrpc.newBuilder()
                .setUuid(String.valueOf(model.getUuid()))
                .setRole(model.getRole())
                .setDescription(model.getDescription())
                .build();
    }

    public static Permission clone(Permission model){
        return Permission.builder()
                .uuid(model.getUuid())
                .role(model.getRole())
                .description(model.getDescription())
                .build();
    }

    public static PermissionGrpc clone(PermissionGrpc grpc){
        return PermissionGrpc.newBuilder()
                .setUuid(grpc.getUuid())
                .setRole(grpc.getRole())
                .setDescription(grpc.getDescription())
                .build();
    }

}
