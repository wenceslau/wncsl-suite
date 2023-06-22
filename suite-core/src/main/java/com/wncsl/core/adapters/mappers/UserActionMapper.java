package com.wncsl.core.adapters.mappers;

import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.dto.UserActionDTO;
import com.wncsl.core.adapters.mappers.dto.UserDTO;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.core.domain.account.entity.PermissionFactory;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.core.domain.account.entity.UserFactory;
import com.wncsl.grpc.audit.UserActionGrpc;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserActionMapper {


    public static UserActionGrpc toGrpc(UserActionDTO dto) {

        return UserActionGrpc.newBuilder()
                .setAction(dto.getAction())
                .setObjectName(dto.getObjectName())
                .setObjectUuid(dto.getObjectUuid())
                .setObjectValue(dto.getObjectValue())
                .setUser((dto.getUserUuid().toString()))
                .setService(dto.getService())
                .setActionAt(dto.getActionAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

    }
}
