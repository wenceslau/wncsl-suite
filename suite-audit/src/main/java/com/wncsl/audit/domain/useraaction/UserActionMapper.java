package com.wncsl.audit.domain.useraaction;


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
                .userUuid(UUID.fromString(userActionGrpc.getUser()))
                .service(userActionGrpc.getService())
                .actionAt(LocalDateTime.parse(userActionGrpc.getActionAt(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public static UserActionGrpc build(UserAction user){

        return UserActionGrpc.newBuilder()
                .setAction(user.getAction())
                .setObjectName(user.getObjectName())
                .setObjectUuid(user.getObjectUuid())
                .setObjectValue(user.getObjectValue())
                .setUser((user.getUserUuid().toString()))
                .setService(user.getService())
                .setActionAt(user.getActionAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    public static UserActionGrpc clone(UserActionGrpc request) {

        return UserActionGrpc.newBuilder()
                .setAction(request.getAction())
                .setObjectName(request.getObjectName())
                .setObjectUuid(request.getObjectUuid())
                .setObjectValue(request.getObjectValue())
                .setUser((request.getUser()))
                .setService(request.getService())
                .setActionAt(request.getActionAt())
                .build();
    }
}
