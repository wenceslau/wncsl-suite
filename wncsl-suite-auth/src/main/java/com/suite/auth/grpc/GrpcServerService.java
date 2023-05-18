package com.suite.auth.grpc;

import com.suite.auth.entity.Account;
import com.suite.auth.service.AccountService;
import com.wncsl.grpc.code.AccountGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@GrpcService
public class GrpcServerService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private AccountService accountService;

    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);


    @Override
    @Secured(value = "ROLE_CREATE_ACCOUNT")
    public void createAccount(AccountGrpc request, StreamObserver<AccountGrpc> responseObserver) {

        String status = "CREATED";
        try {
            accountService.create(Account.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        AccountGrpc reply = AccountGrpc.newBuilder()
                .setId(request.getId())
                .setName(request.getName())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setStatus(status)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info(reply.getStatus());
    }
}