package com.wncsl.auth;

import com.wncsl.auth.domain.permission.Permission;
import com.wncsl.auth.domain.permission.PermissionService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthMainInitialize {

    private UserService userService;

    private PermissionService permissionService;

    private PasswordEncoder passwordEncoder;

    public AuthMainInitialize(UserService userService,
                              PermissionService permissionService,
                              PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.permissionService = permissionService;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " AccountMainInitialize.applicationReadyEvent() - INIT");

        Permission permUser = Permission.builder()
                .uuid(UUID.randomUUID())
                .role("ROLE_USER_GRPC")
                .description("Create user by gRPC")
                .build();
        permissionService.create(permUser);

        Permission permPermission = Permission.builder()
                .uuid(UUID.randomUUID())
                .role("ROLE_PERMISSION_GRPC")
                .description("Create user by gRPC")
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

        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationReadyEvent() - END");

    }

    @EventListener
    private void applicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println(LocalDateTime.now() + " CoreAppListener.applicationStartingEvent()");
    }

}
