package com.suite.auth.entity;

import com.wncsl.grpc.code.AccountGrpc;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {

    @Id
    private UUID id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                '}';
    }

    public static Account build(AccountGrpc accountGrpc){
        return Account.builder()
                .id(UUID.fromString(accountGrpc.getId()))
                .username(accountGrpc.getUsername())
                .password(accountGrpc.getPassword())
                .build();
    }

    public static AccountGrpc build(Account account, String status){
        return AccountGrpc.newBuilder()
                .setId(account.getId().toString())
                .setUsername(account.getUsername())
                .setPassword(account.getPassword())
                .setStatus(status)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
