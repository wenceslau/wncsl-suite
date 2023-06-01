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

        if (userRepository.existsByUsername(user.getUsername())){
           throw new RuntimeException("This user already exist!");
        }

        return userRepository.save(user);
    }

    public User update(User user){

        if (userRepository.existsByUsernameAndUuidIsNot(user.getUsername(), user.getUuid())){
            throw new RuntimeException("This user already exist!");
        }

        User userdb = userRepository.findById(user.getUuid()).get();
        BeanUtils.copyProperties(user, userdb, "uuid", "username");
        return userRepository.save(userdb);
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("No found!"));
    }

    public List<User> listAll(){
        return userRepository.findAll();
    }

}
