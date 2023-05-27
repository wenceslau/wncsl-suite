package com.wncsl.auth.consumer.grpc;

import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import com.wncsl.grpc.code.UserGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc;

import com.wncsl.grpc.code.UserList;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

@GrpcService
public class GrpcServerService extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    @Secured(value = "ROLE_CREATE_USER_GRPC")
    public void createUser(UserGrpc request, StreamObserver<UserGrpc> responseObserver) {

        String status = "CREATED";
        System.out.println(">>>>: "+request.getUuid());

        try {
            userService.create(User.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        UserGrpc reply = UserGrpc.newBuilder()
                .setUuid(request.getUuid())
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
    @Secured(value = "ROLE_CREATE_USER_GRPC")
    public StreamObserver<UserGrpc> createUserStream(StreamObserver<UserList> responseObserver) {

        return new StreamObserver<>() {
            UserList.Builder accountListBuild = UserList.newBuilder();

            @Override
            public void onNext(UserGrpc userGrpc) {
                User user = User.build(userGrpc);
                user =  userService.create(user);

                UserGrpc userGrpcCreated = User.build(user, "CREATED");
                accountListBuild.addUsers(userGrpcCreated);
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage(), throwable);
            }

            @Override
            public void onCompleted() {
                log.info("Processed completed! Records: " + accountListBuild.getUsersCount());
                responseObserver.onNext(accountListBuild.build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
   @Secured(value = "ROLE_CREATE_USER_GRPC")
    public StreamObserver<UserGrpc> createUserStreamBidirectional(StreamObserver<UserGrpc> responseObserver) {

        return new StreamObserver<>() {
            int i = 0;
            @Override
            public void onNext(UserGrpc request) {
                User user = User.build(request);
                user =  userService.create(user);

                UserGrpc reply = User.build(user, "CREATED");
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