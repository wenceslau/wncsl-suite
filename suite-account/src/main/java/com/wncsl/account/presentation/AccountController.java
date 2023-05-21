package com.wncsl.account.presentation;

import com.wncsl.account.application.AccountApplication;
import com.wncsl.account.application.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountApplication accountApplication;

    @GetMapping()
    public ResponseEntity<?> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountApplication.listAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountDTO accountDTO) {
        accountDTO = accountApplication.create(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }
}
