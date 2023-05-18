package com.wncsl.account.entity;

import com.wncsl.grpc.code.AccountGrpc;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                '}';
    }

    public static Account build(AccountGrpc accountGrpc){
        return Account.builder()
                .id(accountGrpc.getId())
                .name(accountGrpc.getName())
                .username(accountGrpc.getUsername())
                .password(accountGrpc.getPassword())
                .build();
    }

    public static AccountGrpc build(Account account){
        return AccountGrpc.newBuilder()
                .setId(account.getId())
                .setName(account.getName())
                .setUsername(account.getUsername())
                .setPassword(account.getPassword())
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
