package com.wncsl.account.infra.grpc;

import com.wncsl.account.application.AccountDTO;
import com.wncsl.grpc.code.AccountGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc.AccountServiceBlockingStub;
import com.wncsl.grpc.code.AccountServiceGrpc.AccountServiceStub;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.List;

@Service
public class GrpcClientService {

    private static final Logger log = LoggerFactory.getLogger(GrpcClientService.class);

    @GrpcClient("auth-grpc-server")
    private AccountServiceBlockingStub accountBlockingStub;

    @GrpcClient("auth-grpc-server")
    private AccountServiceStub accountNonBlockingStub;

    public String createAccount(final AccountDTO accountDTO) {
        try {
            AccountGrpc request = build(accountDTO);
            final AccountGrpc response = this.accountBlockingStub.createAccount(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            System.err.println(e);
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

    public void createCategoryStreamBidirectional(final List<AccountDTO> accountDTOlList){
        StreamObserver<AccountGrpc> streamObserver = new StreamObserver<>() {

            int processed = 0;
            @Override
            public void onNext(AccountGrpc next) {
                processed++;
                log.info("Processed: " + next.getId() + " "+ next.getStatus());
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

        StreamObserver<AccountGrpc> requestObserver = accountNonBlockingStub.createAccountStreamBidirectional(streamObserver);

        try {
            for (AccountDTO accountDTO : accountDTOlList) {
                AccountGrpc accountGrpc = build(accountDTO);
                requestObserver.onNext(accountGrpc);
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
        }

        requestObserver.onCompleted();
    }

    public AccountGrpc build(AccountDTO dto){
        return AccountGrpc.newBuilder()
                .setId(dto.getId().toString())
                .setName(dto.getName())
                .setUsername(dto.getUsername())
                .setPassword(dto.getPassword())
                .build();
    }

}