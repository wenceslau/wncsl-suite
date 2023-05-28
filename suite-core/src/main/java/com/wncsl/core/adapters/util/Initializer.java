package com.wncsl.core.adapters.util;

import com.wncsl.core.adapters.inbound.rest.account.service.PermissionService;
import com.wncsl.core.adapters.inbound.rest.account.service.UserService;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Initializer {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

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

        permissionDTO = PermissionDTO.builder().description("Authorize create user")
                .role("ROLE_CREATE_USER").build();
        lst.add(permissionService.create(permissionDTO));

        permissionDTO = PermissionDTO.builder().description("Authorize update user")
                .role("ROLE_UPDATE_USER").build();
        lst.add(permissionService.create(permissionDTO));

        UserDTO user = userService.findByUsername("admin");
        userService.addPermissions(user.getUuid(), lst);

    }

}
