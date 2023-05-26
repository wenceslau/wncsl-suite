package com.wncsl.core.infra.domain.account.repository;

import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.repository.UserRepository;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class UserRepositoryImpl implements UserRepository {

    private UserJpaRepository userJpaRepository;
    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @Override
    public UUID create(User entity) {
        return userJpaRepository.save(entity.toModel()).getUuid();
    }

    @Override
    public UUID update(User entity) {
        return userJpaRepository.save(entity.toModel()).getUuid();
    }

    @Override
    public User find(UUID uuid) {
        return userJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Not found!"))
                .toEntity();
    }

    @Override
    public Set<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(a-> a.toEntity())
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existByUsernameAndNotEqualsId(String username, UUID id) {
        return false;
    }
}
