package com.wncsl.core.presentation.account.controller;

import com.wncsl.core.presentation.account.dto.UserDTO;
import com.wncsl.core.presentation.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.listAll());
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        userDTO = userService.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
