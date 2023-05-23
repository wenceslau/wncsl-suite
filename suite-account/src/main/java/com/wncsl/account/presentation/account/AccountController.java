package com.wncsl.account.presentation.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping()
    public ResponseEntity<List<AccountDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.listAll());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO accountDTO) {
        accountDTO = accountService.create(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }
}
