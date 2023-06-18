package com.wncsl.audit.domain.useraaction;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActionService {

    private UserActionRepository userActionRepository;

    public UserActionService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    public UserAction create(UserAction userAction){

        return userActionRepository.save(userAction);
    }

    public List<UserAction> listAll(){

        return userActionRepository.findAll();
    }

}