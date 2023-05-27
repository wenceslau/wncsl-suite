package com.wncsl.core;

import com.wncsl.core.adapters.inbound.rest.account.controller.PermissionController;
import com.wncsl.core.adapters.inbound.rest.account.controller.UserController;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CoreMainInitialize {

    @Autowired
    private UserController userController;

    @Autowired
    private PermissionController permissionController;

    @EventListener
    private void applicationReadyEvent(ApplicationReadyEvent event) {

        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationReadyEvent() - INIT");

        PermissionDTO permissionDTO = PermissionDTO.builder()
                .description("Create User")
                .role("ROLE_CREATE_USER")
                .build();
//
        permissionDTO = permissionController.create(permissionDTO).getBody();
        System.out.println("Permission created: " + permissionDTO.getUuid());
//
//        PermissionDTO pdto = permissionController.findById(permissionDTO.getUuid()).getBody();
//        System.out.println("Permission created: " + pdto.getUuid());


        UserDTO userDTO = UserDTO.builder()
                .name("Administrator")
                .username("admin")
                .password("123456")
                .permissions(List.of(permissionDTO))
                .build();

        userDTO =  userController.create(userDTO).getBody();

        System.out.println("User created: " + userDTO.getUuid());

        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationReadyEvent() - END");

    }

    @EventListener
    private void applicationStartedEvent(ApplicationStartedEvent event) {
        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationStartedEvent()");
    }

    @EventListener
    private void applicationStartingEvent(ApplicationStartingEvent event) {
        System.out.println(LocalDateTime.now() + " CoreMainInitialize.applicationStartingEvent()");
    }

}
