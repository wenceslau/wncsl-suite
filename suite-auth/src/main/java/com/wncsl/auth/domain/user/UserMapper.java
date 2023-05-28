package com.wncsl.auth.domain.user;

import com.wncsl.auth.domain.permission.Permission;
import com.wncsl.auth.domain.permission.PermissionMapper;
import com.wncsl.grpc.code.PermissionGrpc;
import com.wncsl.grpc.code.UserGrpc;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    public static User build(UserGrpc userGrpc){

        Set<Permission> lst = userGrpc.getPermissionsList()
                .stream()
                .map(p -> PermissionMapper.build(p))
                .collect(Collectors.toSet());

        return User.builder()
                .uuid(UUID.fromString(userGrpc.getUuid()))
                .username(userGrpc.getUsername())
                .password(userGrpc.getPassword())
                .type("APP")
                .permissions(lst)
                .build();
    }

    public static UserGrpc build(User user, String status){

        List<PermissionGrpc> lst = user.getPermissions()
                .stream()
                .map(p -> PermissionMapper.build(p, status))
                .collect(Collectors.toList());

        return UserGrpc.newBuilder()
                .setUuid(user.getUuid().toString())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setStatus(status)
                .addAllPermissions(lst)
                .build();
    }

    public static UserGrpc clone(UserGrpc request, String status) {

        List<PermissionGrpc> lst = request.getPermissionsList()
                .stream()
                .map(p -> PermissionMapper.clone(p, status))
                .collect(Collectors.toList());

        return UserGrpc.newBuilder()
                .setUuid(request.getUuid().toString())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setStatus(status)
                .addAllPermissions(lst)
                .build();

    }
}
