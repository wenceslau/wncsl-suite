package com.wncsl.core.adapters.outbound.grpc;

import com.wncsl.core.adapters.mappers.PermissionMapper;
import com.wncsl.core.adapters.mappers.UserMapper;
import com.wncsl.core.adapters.outbound.persistence.account.model.PermissionModel;
import com.wncsl.core.adapters.outbound.persistence.account.model.UserModel;
import com.wncsl.grpc.account.*;
import com.wncsl.grpc.account.AccountServiceGrpc.AccountServiceBlockingStub;
import com.wncsl.grpc.account.AccountServiceGrpc.AccountServiceStub;

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

    public String addUser(final UserModel user, OPERATION operation) {
        try {
            UserGrpc request = UserMapper.toGrpc(user, operation);
            final Response response = this.accountBlockingStub.addUser(request);
            if (STATUS.FAILURE.equals(response.getStatus())){
                log.error(response.getMessage());
            }
            return response.getStatus().name();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            addUser(user,operation);
            return error;
        }
    }

    public String addPermission(final PermissionModel permission, OPERATION operation) {
        try {
            PermissionGrpc request = PermissionMapper.toGrpc(permission, operation);
            final Response response = this.accountBlockingStub.addPermission(request);
            if (STATUS.FAILURE.equals(response.getStatus())){
                log.error(response.getMessage());
            }
            return response.getStatus().name();
        } catch (final StatusRuntimeException e) {
            String error = "FAILED with " + e;
            log.error(error);
            try {Thread.sleep(1000);} catch (InterruptedException ex) { }
            addPermission(permission, operation);
            return error;
        }
    }

    public void createCategoryStreamBidirectional(final List<UserModel> userList){
        StreamObserver<UserGrpc> streamObserver = new StreamObserver<>() {

            int processed = 0;
            @Override
            public void onNext(UserGrpc next) {
                processed++;
                log.info("Processed: " + next.getUuid() + " "+ next.getOperation());
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
            for (UserModel user : userList) {
                UserGrpc userGrpc = UserMapper.toGrpc(user, OPERATION.INSERT);
                requestObserver.onNext(userGrpc);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
        }

        requestObserver.onCompleted();
    }
}