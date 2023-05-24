package com.wncsl.account.infra.domain.account.repository;

import com.wncsl.account.infra.domain.account.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountModel, UUID> {

    boolean existsByUsername(String username);

}
