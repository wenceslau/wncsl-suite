package com.wncsl.auth.domain.user;


import org.springframework.beans.BeanUtils;
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

    public User update(User user){
        User userdb = userRepository.findById(user.getUuid()).get();
        BeanUtils.copyProperties(user, userdb);
        return userRepository.save(userdb);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("No found!"));
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

}
