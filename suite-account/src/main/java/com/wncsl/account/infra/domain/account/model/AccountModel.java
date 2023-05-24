package com.wncsl.account.infra.domain.account.model;

import com.wncsl.account.domain.account.entity.Account;
import com.wncsl.account.presentation.account.dto.AccountDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                '}';
    }

    public Account toEntity(){
        return Account.fromModel(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountModel accountModel = (AccountModel) o;

        return id.equals(accountModel.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public AccountDTO toDTO(){
        return AccountDTO.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .build();
    }
}
