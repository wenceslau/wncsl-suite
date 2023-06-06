package com.wncsl.core.adapters.outbound.persistence.account.repository.ports;

import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.adapters.outbound.persistence.account.repository.UserJpaRepository;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.ports.UserPersistencePort;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Transactional
public class UserPersistencePortImpl implements UserPersistencePort {

    private UserJpaRepository userJpaRepository;

    public UserPersistencePortImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UUID create(User entity) {
        UserModel model = UserMapper.toModel(entity);
        model.generateHash();
        return userJpaRepository.save(model).getUuid();
    }

    @Override
    public UUID update(User entity) {
        UserModel model = UserMapper.toModel(entity);
        model.generateHash();
        return userJpaRepository.save(model).getUuid();
    }

    @Override
    public User findById(UUID uuid) {

        UserModel model = userJpaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("ID "+uuid+" not found!"));

        return UserMapper.toEntity(model);
    }

    @Override
    public boolean existByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existByUsernameAndNotEqualsId(String username, UUID id) {
        return false;
    }

    @Override
    public User findByUsername(String username) {
        UserModel model = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Username "+username+" not found!"));

        return UserMapper.toEntity(model);
    }
}
