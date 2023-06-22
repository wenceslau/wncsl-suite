package com.wncsl.core.adapters.outbound.persistence;

import com.wncsl.core.adapters.mappers.dto.UserActionDTO;
import com.wncsl.core.adapters.outbound.grpc.GrpcAccountClientService;
import com.wncsl.core.adapters.outbound.grpc.GrpcAuditClientService;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.adapters.outbound.persistence.account.repository.PermissionJpaRepository;
import com.wncsl.grpc.account.ACTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Component
public class ListenerModel {
    static private GrpcAccountClientService grpcAccountClientService;
    static private GrpcAuditClientService grpcAuditClientService;

    @Autowired
    public void init(GrpcAccountClientService grpcAccountClientService,
                     GrpcAuditClientService grpcAuditClientService) {
        ListenerModel.grpcAccountClientService = grpcAccountClientService;
        ListenerModel.grpcAuditClientService = grpcAuditClientService;
        System.out.println("Initializing with dependency GRPC");
    }

    @PreRemove
    public void preRemove(Model model) {
        System.out.println("preRemove: " + model);
    }

    @PrePersist
    public void prePersist(Model model) {
        model.setCreated(LocalDateTime.now());
        model.setUpdated(LocalDateTime.now());
        System.out.println("@PrePersist: " + model);
    }

    @PreUpdate
    public void preUpdate(Model model) {
        model.setUpdated(LocalDateTime.now());
        System.out.println("@PreUpdate: " + model);
    }

    @PostPersist
    public void postPersist(Model model) {
        System.out.println("@PostPersist: "+model);
        sendToSuiteAudit(model, ACTION.CREATE);
        sendToSuiteAuth(model, ACTION.CREATE);
    }

    @PostUpdate
    public void postUpdate(Model model) {
        System.out.println("@PostUpdate: "+model);
        sendToSuiteAudit(model, ACTION.UPDATE);
        sendToSuiteAuth(model, ACTION.UPDATE);
    }

    @PostRemove
    public void postDelete(Model model) {
        System.out.println(model);
        sendToSuiteAudit(model, ACTION.DELETE);
    }

    private void sendToSuiteAuth(Model model, ACTION action){
        if (model instanceof UserModel){
            grpcAccountClientService.addUser((UserModel) model, action);
        }else if (model instanceof PermissionModel){
            grpcAccountClientService.addPermission((PermissionModel) model, action);
        }
    }

    private void sendToSuiteAudit(Model model, ACTION action){
        UserActionDTO actionDTO = UserActionDTO.builder()
                .service("suite-core")
                .action(String.valueOf(action))
                .userUuid("f4441422-112b-11ee-be56-0242ac120002")
                .objectName(model.getClass().getSimpleName())
                .objectUuid(String.valueOf(model.getUuid()))
                .objectValue(model.toString())
                .actionAt(LocalDateTime.now())
                .build();

        grpcAuditClientService.addUserAction(actionDTO);
    }
}
