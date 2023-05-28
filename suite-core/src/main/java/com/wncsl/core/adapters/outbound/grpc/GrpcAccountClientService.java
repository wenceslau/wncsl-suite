package com.wncsl.core.adapters.outbound.grpc;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.adapters.mappers.dto.PermissionDTO;
import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.domain.account.entity.Permission;
import com.wncsl.core.domain.account.entity.User;
import com.wncsl.grpc.code.AccountServiceGrpc.AccountServiceBlockingStub;
import com.wncsl.grpc.code.AccountServiceGrpc.AccountServiceStub;

import com.wncsl.grpc.code.PermissionGrpc;
import com.wncsl.grpc.code.UserGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class GrpcAccountClientService {

    private static final Logger log = LoggerFactory.getLogger(GrpcAccountClientService.class);

    @GrpcClient("auth-grpc-server")
    private AccountServiceBlockingStub accountBlockingStub;

    @GrpcClient("auth-grpc-server")
    private AccountServiceStub accountNonBlockingStub;

    public String createUser(final User user) {
        try {
            UserGrpc request = UserMapper.toGrpc(user);
            final UserGrpc response = this.accountBlockingStub.createUser(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            createUser(user);
            return error;
        }
    }

    public String updateUser(final User user) {
        try {
            UserGrpc request = UserMapper.toGrpc(user);
            final UserGrpc response = this.accountBlockingStub.updateUser(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            updateUser(user);
            return error;
        }
    }


    public String createPermission(final Permission permission) {
        try {
            PermissionGrpc request = PermissionMapper.toGrpc(permission);
            final PermissionGrpc response = this.accountBlockingStub.createPermission(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            createPermission(permission);
            return error;
        }
    }

    public String updatePermission(final Permission permission) {
        try {
            PermissionGrpc request = PermissionMapper.toGrpc(permission);
            final PermissionGrpc response = this.accountBlockingStub.updatePermission(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            updatePermission(permission);
            return error;
        }
    }

    public void createCategoryStreamBidirectional(final List<User> userList){
        StreamObserver<UserGrpc> streamObserver = new StreamObserver<>() {

            int processed = 0;
            @Override
            public void onNext(UserGrpc next) {
                processed++;
                log.info("Processed: " + next.getUuid() + " "+ next.getStatus());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Send completed. Num Processed: " + processed);
            }
        };

        StreamObserver<UserGrpc> requestObserver = accountNonBlockingStub.createUserStreamBidirectional(streamObserver);

        try {
            for (User user : userList) {
                UserGrpc userGrpc = UserMapper.toGrpc(user);
                requestObserver.onNext(userGrpc);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
        }

        requestObserver.onCompleted();
    }
}