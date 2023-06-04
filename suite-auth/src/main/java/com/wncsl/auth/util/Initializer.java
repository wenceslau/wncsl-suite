package com.wncsl.auth.util;

import com.wncsl.auth.domain.permission.Permission;
import com.wncsl.auth.domain.permission.PermissionService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class Initializer {

    private final UserService userService;
    private final PermissionService permissionService;
    private final PasswordEncoder passwordEncoder;

    public Initializer(UserService userService, PermissionService permissionService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
    }

    public void init(){
        createUserPermission();
    }

    private void createUserPermission(){

        Permission permUser = Permission.builder()
                .uuid(UUID.randomUUID())
                .role("ROLE_USER_GRPC")
                .description("Create user by gRPC")
                .build();
        permissionService.create(permUser);

        Permission permPermission = Permission.builder()
                .uuid(UUID.randomUUID())
                .role("ROLE_PERMISSION_GRPC")
                .description("Create permission by gRPC")
                .build();
        permissionService.create(permPermission);

        User user = User.builder()
                .uuid( UUID.randomUUID())
                .username("api")
                .password(passwordEncoder.encode("api123"))
                .type("GRPC")
                .permissions(Set.of(permUser, permPermission))
                .build();
        userService.create(user);

    }
}
