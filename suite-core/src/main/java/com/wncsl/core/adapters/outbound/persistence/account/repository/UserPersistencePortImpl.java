package com.wncsl.core.adapters.outbound.persistence.account.repository;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.UserPersistencePort;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class UserPersistencePortImpl implements UserPersistencePort {

    private UserJpaRepository userJpaRepository;

    public UserPersistencePortImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UUID create(User entity) {
        return userJpaRepository.save(UserMapper.toModel(entity)).getUuid();
    }

    @Override
    public UUID update(User entity) {
        return userJpaRepository.save(UserMapper.toModel(entity)).getUuid();
    }

    @Override
    public User findById(UUID uuid) {

        UserModel model = userJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("ID "+uuid+" not found!"));

        return UserMapper.toEntity(model);
    }

    @Override
    public Set<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(model -> UserMapper.toEntity(model))
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
