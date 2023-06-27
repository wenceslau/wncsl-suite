package com.wncsl.audit.domain.useraaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserActionService {

    private UserActionRepository userActionRepository;

    public UserActionService(UserActionRepository userActionRepository) {
        this.userActionRepository = userActionRepository;
    }

    public UserAction create(UserAction userAction){
        userAction.setCreated(LocalDateTime.now());
        return userActionRepository.save(userAction);
    }

    public Page<UserAction> listAll(Pageable pageable, String uuid, String service, String objectName){

        return userActionRepository.findAllByObjectUuidAndServiceAndObjectName(pageable, uuid, service, objectName);
    }

}