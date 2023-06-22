package com.wncsl.audit.domain.useraaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-action")
public class UserActionController {

    private final UserActionService userActionService;

    public UserActionController(UserActionService userActionService) {
        this.userActionService = userActionService;
    }

    @GetMapping()
    public ResponseEntity<Object> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userActionService.listAll());
    }
}