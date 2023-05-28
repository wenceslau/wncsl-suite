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

        UUID uuid = UUID.randomUUID();

        Permission permission = Permission.builder()
                .uuid(uuid)
                .role("ROLE_CREATE_USER_GRPC")
                .description("Create user by gRPC")
                .build();
        permissionService.create(permission);
        System.out.println("Permission created: " + permission.getUuid());

        uuid = UUID.randomUUID();
        User user = User.builder()
                .uuid(uuid)
                .username("api")
                .password(passwordEncoder.encode("api123"))
                .type("GRPC")
                .permissions(Set.of(permission))
                .build();
        user =  userService.create(user);
        System.out.println("User created: " + user.getUuid());

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
