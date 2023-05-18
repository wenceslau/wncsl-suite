package com.wncsl.account.grpc;

import com.wncsl.account.entity.Account;
import com.wncsl.grpc.code.AccountGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc.AccountServiceBlockingStub;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class GrpcClientService {

    @GrpcClient("local-grpc-server")
    private AccountServiceBlockingStub accountBlockingStub;

    public String createAccount(final Account account) {
        try {
            AccountGrpc request = Account.build(account);
            final AccountGrpc response = this.accountBlockingStub.createAccount(request);
            return response.getStatus();
        } catch (final StatusRuntimeException e) {
            System.err.println(e);
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

}