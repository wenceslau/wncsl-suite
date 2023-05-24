package com.wncsl.account.presentation.account.controller;

import com.wncsl.account.presentation.account.dto.AccountDTO;
import com.wncsl.account.presentation.account.dto.PermissionDTO;
import com.wncsl.account.presentation.account.service.AccountService;
import com.wncsl.account.presentation.account.service.PermissionService;
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
