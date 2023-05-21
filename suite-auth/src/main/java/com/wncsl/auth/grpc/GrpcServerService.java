package com.wncsl.auth.grpc;

import com.wncsl.auth.entity.Account;
import com.wncsl.auth.service.AccountService;
import com.wncsl.grpc.code.AccountGrpc;
import com.wncsl.grpc.code.AccountList;
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

    @Override
    @Secured(value = "ROLE_CREATE_ACCOUNT")
    public StreamObserver<AccountGrpc> createAccountStream(StreamObserver<AccountList> responseObserver) {

        return new StreamObserver<>() {
            AccountList.Builder accountListBuild = AccountList.newBuilder();

            @Override
            public void onNext(AccountGrpc accountGrpc) {
                Account account = Account.build(accountGrpc);
                account =  accountService.create(account);

                AccountGrpc accountGrpcCreated = Account.build(account, "CREATED");
                accountListBuild.addAccounts(accountGrpcCreated);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Processed completed! Records: " + accountListBuild.getAccountsCount());
                responseObserver.onNext(accountListBuild.build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    @Secured(value = "ROLE_CREATE_ACCOUNT")
    public StreamObserver<AccountGrpc> createAccountStreamBidirectional(StreamObserver<AccountGrpc> responseObserver) {

        return new StreamObserver<>() {
            int i = 0;
            @Override
            public void onNext(AccountGrpc request) {
                Account account = Account.build(request);
                account =  accountService.create(account);

                AccountGrpc reply = Account.build(account, "CREATED");
                responseObserver.onNext(reply);
                i++;
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Received completed! Records: " + i);
                responseObserver.onCompleted();
            }
        };

    }
}