package com.wncsl.account.presentation.account.service;

import com.wncsl.account.domain.account.entity.Account;
import com.wncsl.account.domain.account.repository.AccountRepository;
import com.wncsl.account.domain.account.service.AccountDomainService;
import com.wncsl.account.infra.grpc.GrpcClientService;
import com.wncsl.account.presentation.account.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private GrpcClientService grpcClientService;

    private AccountDomainService domainAccountService;

    public AccountService(AccountRepository accountRepository) {
        this.domainAccountService = new AccountDomainService(accountRepository);
    }

    public AccountDTO create(AccountDTO accountDTO){

        Account account = accountDTO.toEntity();

        domainAccountService.create(account);

        accountDTO = account.toDTO();

        //grpcClientService.createAccount(accountDTO);
        //AccountClientGrpc accountClientGrpc = new AccountClientGrpc("localhost", 9090);
        //accountClientGrpc.createAccount(account);

        return accountDTO;
    }

    public AccountDTO update(UUID id, AccountDTO accountDTO){

        Account account  = domainAccountService.findById(id);
        account.changeName(accountDTO.getName());
        domainAccountService.update(account);

        return accountDTO;
    }

    public void changePassword(UUID id, String oldPassword, String newPassword){

        Account account  = domainAccountService.findById(id);
        account.changePassword(oldPassword, newPassword);

        domainAccountService.update(account);
    }

    public List<AccountDTO> listAll() {

        return domainAccountService.fildAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }
}
