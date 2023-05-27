package com.wncsl.core.adapters.inbound.rest.account.controller;

import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.inbound.rest.account.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping()
    public ResponseEntity<List<PermissionDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.listAll());
    }

    @PostMapping
    public ResponseEntity<PermissionDTO> create(@RequestBody PermissionDTO permissionDTO) {
        permissionDTO = permissionService.create(permissionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionDTO);
    }

    @GetMapping("/uuid")
    public ResponseEntity<PermissionDTO> findById(UUID uuid) {
        PermissionDTO permissionDTO = permissionService.findById(uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionDTO);
    }
}
