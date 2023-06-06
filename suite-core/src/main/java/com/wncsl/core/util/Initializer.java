package com.wncsl.core.util;

import com.wncsl.core.adapters.inbound.account.service.PermissionService;
import com.wncsl.core.adapters.inbound.account.service.UserService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Initializer {

    private final UserService userService;

    private final PermissionService permissionService;

    public Initializer(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    public void init(){
        createUsers();
        createPermissions();
    }

    private void createUsers(){
        UserDTO userDTO = UserDTO.builder()
                .name("Administrator")
                .username("admin")
                .password("123456")
                .build();
        userDTO =  userService.create(userDTO);
        System.out.println("User created: " + userDTO.getUuid());
    }

    private void createPermissions(){

        PermissionDTO permissionDTO;
        List<PermissionDTO> lst = new ArrayList<>();

        permissionDTO = PermissionDTO.builder().description("Authorize create permission")
                .role("ROLE_CREATE_PERMISSION").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize update permission")
                .role("ROLE_UPDATE_PERMISSION").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize view permission")
                .role("ROLE_VIEW_PERMISSION").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize create user")
                .role("ROLE_CREATE_USER").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize update user")
                .role("ROLE_UPDATE_USER").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize view user")
                .role("ROLE_VIEW_USER").build();
        lst.add(permissionService.create(permissionDTO));

        UserDTO userDTO = userService.findByUsername("admin");
        userService.addPermissions(userDTO.getUuid(), lst);
    }

}
