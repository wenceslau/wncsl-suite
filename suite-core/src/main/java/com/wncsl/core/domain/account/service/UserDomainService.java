package com.wncsl.core.domain.account.service;

import com.wncsl.core.domain.BusinessException;
import com.wncsl.core.domain._shared.InterfaceDomainService;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.UUID;

public class UserDomainService implements InterfaceDomainService<User> {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID create(User user){

        usernameAlreadyExist(user.getUsername());

        UUID uuid = userRepository.create(user);

        return uuid;

    }

    public void update(User user){

        userRepository.update(user);

    }

    public Set<User> fildAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id){
        return userRepository.find(id);
    }

    private void usernameAlreadyExist(String username){

        boolean exist = userRepository.existByUsername(username);

        if (exist){
            throw new BusinessException("This username had already exist");
        }

    }

}
