package com.wncsl.core.adapters.inbound.rest.account.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.inbound.rest.account.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.wncsl.core.adapters.mappers.dto.View.*;

@RestController
@RequestMapping("/permissions")
public class PermissionController  {

    private final String module = "PERMISSION";
    private final String authUpdate = "hasAnyAuthority('ROLE_UPDATE_"+module+"')";
    private final String authCreate = "hasAnyAuthority('ROLE_CREATE_"+module+"')";
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping()
    @JsonView({Full.class})
    public ResponseEntity<List<PermissionDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.listAll());
    }

    @PostMapping
    @JsonView({Full.class})
    @PreAuthorize(authCreate)
    public ResponseEntity<PermissionDTO> create(
            @RequestBody @JsonView(Insert.class)  PermissionDTO permissionDTO) {

        permissionDTO = permissionService.create(permissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionDTO);
    }

    @PutMapping("{uuid}")
    @JsonView({Full.class})
    @PreAuthorize(authUpdate)
    public ResponseEntity<PermissionDTO> update(
            @PathVariable UUID uuid,
            @RequestBody @JsonView(Update.class)  PermissionDTO permissionDTO) {

        permissionDTO = permissionService.update(uuid, permissionDTO);
        return ResponseEntity.status(HttpStatus.OK).body(permissionDTO);
    }

    @GetMapping("/uuid")
    @JsonView({Full.class})
    public ResponseEntity<PermissionDTO> findById(UUID uuid) {
        PermissionDTO permissionDTO = permissionService.findById(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(permissionDTO);
    }


}
