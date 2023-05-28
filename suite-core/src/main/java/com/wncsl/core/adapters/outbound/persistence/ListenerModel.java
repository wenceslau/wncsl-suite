package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class ListenerModel {
    static private PermissionJpaRepository permissionJpaRepository;

    @Autowired
    public void init(PermissionJpaRepository permissionJpaRepository) {
        ListenerModel.permissionJpaRepository = permissionJpaRepository;
        System.out.println("Initializing with dependency ["+ permissionJpaRepository +"]");
    }

    @PostPersist
    public void postPersist(Model target) {
        System.out.println(">>>>>>>>>>"+target);
    }

    @PostUpdate
    public void postUpdate(Model target) {
        System.out.println(target);
    }

    @PostRemove
    public void postDelete(Model target) {
        System.out.println(target);
    }

}
