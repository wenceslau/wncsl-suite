package com.wncsl.core.adapters.inbound.rest.account.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import com.wncsl.core.adapters.inbound.rest.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.wncsl.core.adapters.mappers.dto.View.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    @JsonView({Full.class})
    public ResponseEntity<List<UserDTO>> listAll(
            @PageableDefault() Pageable pageable) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.listAll(pageable));
    }

    @PostMapping
    @JsonView({Full.class})
    public ResponseEntity<UserDTO> create(
            @RequestBody @JsonView(Insert.class) UserDTO userDTO) {

        userDTO = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PutMapping("/{uuid}")
    @JsonView({Full.class})
    public ResponseEntity<UserDTO> update(
            @PathVariable UUID uuid,
            @RequestBody @JsonView(Update.class) UserDTO userDTO) {

        userDTO = userService.update(uuid, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PutMapping("/{userUuid}/permission")
    @JsonView({Resume.class})
    public ResponseEntity<UserDTO> addPermission(
            @PathVariable UUID userUuid,
            @RequestBody List<PermissionDTO> permissionDTOList) {
        System.out.println("trace");
        UserDTO userDTO = userService.addPermissions(userUuid, permissionDTOList);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @GetMapping("/{uuid}")
    @JsonView({Full.class})
    public ResponseEntity<UserDTO> findById(
            @PathVariable UUID uuid) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(uuid));
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @PathVariable UUID uuid,
            @RequestBody String value) {

        userService.changePassword(uuid, value);
    }

}
