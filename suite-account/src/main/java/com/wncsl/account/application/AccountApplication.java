package com.wncsl.account.application;

import com.wncsl.account.domain.entity.Account;
import com.wncsl.account.domain.repository.AccountRepository;
import com.wncsl.account.infra.grpc.GrpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountApplication {

    @Autowired
    private GrpcClientService grpcClientService;

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO create(AccountDTO accountDTO){

        Account account = accountDTO.toEntity();

        UUID uuid = accountRepository.create(account);

        accountDTO = account.toDTO();
        accountDTO.setId(uuid);

        grpcClientService.createAccount(accountDTO);
        //AccountClientGrpc accountClientGrpc = new AccountClientGrpc("localhost", 9090);
        //accountClientGrpc.createAccount(account);

        return accountDTO;

    }

    public AccountDTO update(UUID id, AccountDTO accountDTO){

        Account accountToSave  = accountRepository.find(id);

        accountToSave.changeName(accountDTO.getName());

        UUID uuid = accountRepository.update(accountToSave);

        return accountToSave.toDTO();

    }

    public void changePassword(UUID id, String oldPassword, String newPassword){

        Account accountToSave  = accountRepository.find(id);
        accountToSave.changePassword(oldPassword, newPassword);

        accountRepository.update(accountToSave);

    }

    public List<AccountDTO> listAll() {
        return accountRepository.findAll()
                .stream()
                .map(a -> a.toDTO())
                .collect(Collectors.toList());
    }
}
