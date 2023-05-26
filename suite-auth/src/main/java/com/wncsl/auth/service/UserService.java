package com.wncsl.auth.service;


import com.wncsl.auth.entity.User;
import com.wncsl.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("No found!"));
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

}
