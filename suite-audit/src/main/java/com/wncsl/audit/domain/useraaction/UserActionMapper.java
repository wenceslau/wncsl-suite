package com.wncsl.audit.domain.useraaction;


import com.wncsl.grpc.audit.OPERATION;
import com.wncsl.grpc.audit.UserActionGrpc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class UserActionMapper {

    public static UserAction build(UserActionGrpc userActionGrpc){

        return UserAction.builder()
                .action(userActionGrpc.getAction())
                .objectName(userActionGrpc.getObjectName())
                .objectUuid(userActionGrpc.getObjectUuid())
                .objectValue(userActionGrpc.getObjectValue())
                .userUuid(UUID.fromString(userActionGrpc.getUserUuid()))
                .service(userActionGrpc.getService())
                .actionAt(LocalDateTime.parse(userActionGrpc.getActionAt(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public static UserActionGrpc build(UserAction user, OPERATION operation){

        return UserActionGrpc.newBuilder()
                .setService(user.getService())
                .setAction(user.getAction())
                .setObjectName(user.getObjectName())
                .setObjectUuid(user.getObjectUuid())
                .setObjectValue(user.getObjectValue())
                .setUserUuid(user.getUserUuid().toString())
                .setActionAt(user.getActionAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .setOperation(operation)
                .build();
    }

    public static UserActionGrpc clone(UserActionGrpc request) {

        return UserActionGrpc.newBuilder()
                .setAction(request.getAction())
                .setObjectName(request.getObjectName())
                .setObjectUuid(request.getObjectUuid())
                .setObjectValue(request.getObjectValue())
                .setUserUuid((request.getUserUuid()))
                .setService(request.getService())
                .setActionAt(request.getActionAt())
                .setOperation(request.getOperation())
                .build();
    }
}
