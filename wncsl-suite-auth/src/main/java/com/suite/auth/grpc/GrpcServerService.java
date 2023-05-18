package com.suite.auth.grpc;

import com.suite.auth.entity.Account;
import com.suite.auth.service.AccountService;
import com.wncsl.grpc.code.AccountGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class GrpcServerService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private AccountService accountService;

    @Override
    public void createAccount(AccountGrpc request, StreamObserver<AccountGrpc> responseObserver) {

        String status = "CREATED";
        try {
            accountService.create(Account.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            System.err.println(ex);
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

        System.out.println(reply.getStatus());
    }
}