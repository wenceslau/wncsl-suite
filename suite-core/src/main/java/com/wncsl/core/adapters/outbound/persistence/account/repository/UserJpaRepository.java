package com.wncsl.core.adapters.outbound.persistence.account.repository;

import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.domain.account.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends PagingAndSortingRepository<UserModel, UUID> {

    Optional<UserModel> findByUuid(UUID uuid);

    boolean existsByUsername(String username);

    Optional<UserModel> findByUsername(String username);
}
