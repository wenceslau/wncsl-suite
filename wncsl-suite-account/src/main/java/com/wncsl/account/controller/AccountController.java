package com.wncsl.account.controller;

import com.wncsl.account.entity.Account;
import com.wncsl.account.grpc.GrpcClientService;
import com.wncsl.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private GrpcClientService grpcClientService;

    @GetMapping()
    public ResponseEntity<?> listAll() {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.listAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Account account) {
        account = accountService.create(account);
        String ret = grpcClientService.createAccount(account);
        System.out.println(ret);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(account));
    }
}
