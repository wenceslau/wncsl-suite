package com.wncsl.core.presentation.account.controller;

import com.wncsl.core.presentation.account.dto.PermissionDTO;
import com.wncsl.core.presentation.account.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
