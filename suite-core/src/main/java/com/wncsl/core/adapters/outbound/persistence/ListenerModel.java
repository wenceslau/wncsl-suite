package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import com.wncsl.grpc.code.ACTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
public class ListenerModel {
    static private GrpcAccountClientService grpcAccountClientService;

    @Autowired
    public void init(GrpcAccountClientService grpcAccountClientService) {
        ListenerModel.grpcAccountClientService = grpcAccountClientService;
        System.out.println("Initializing with dependency ["+ grpcAccountClientService +"]");
    }

    @PreRemove
    public void preRemove(Model model) {
        System.out.println("preRemove: " + model);
    }

    @PrePersist
    public void prePersist(Model model) {
        model.setCreated(LocalDateTime.now());
        System.out.println("prePersist: " + model);
    }

    @PreUpdate
    public void preUpdate(Model model) {
        model.setUpdated(LocalDateTime.now());
        System.out.println("preUpdate: " + model);
    }

    @PostPersist
    public void postPersist(Model model) {
        System.out.println(">>>>>>>>>>"+model);
        sendToSuiteAuth(model, ACTION.CREATE);
    }

    @PostUpdate
    public void postUpdate(Model model) {
        System.out.println(model);
        sendToSuiteAuth(model, ACTION.UPDATE);
    }

    @PostRemove
    public void postDelete(Model target) {
        System.out.println(target);
    }

    private void sendToSuiteAuth(Model model, ACTION action){
        if (model instanceof UserModel){
            grpcAccountClientService.sendUser((UserModel) model, action);
        }else if (model instanceof PermissionModel){
            grpcAccountClientService.sendPermission((PermissionModel) model, action);
        }
    }
}
