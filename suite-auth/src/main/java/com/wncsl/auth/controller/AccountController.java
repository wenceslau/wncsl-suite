package com.wncsl.auth.controller;

import com.wncsl.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AccountController {

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<Object> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.listAll());
    }
}
