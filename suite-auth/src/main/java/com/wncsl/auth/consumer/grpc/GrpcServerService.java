package com.wncsl.auth.consumer.grpc;

import com.wncsl.auth.domain.permission.PermissionMapper;
import com.wncsl.auth.domain.permission.PermissionService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserMapper;
import com.wncsl.auth.domain.user.UserService;

import com.wncsl.grpc.account.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class GrpcServerService extends AccountServiceGrpc.AccountServiceImplBase {

    private final UserService userService;

    private final PermissionService permissionService;

    public GrpcServerService(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    private static final Logger log = LoggerFactory.getLogger(GrpcServerService.class);

    @Override
    //@Secured(value = "ROLE_USER_GRPC")
    public void addUser(UserGrpc request, StreamObserver<Response> responseObserver) {

        String message;
        STATUS status = STATUS.SUCCESS;
        log.info(">>>>: Received "+request.getClass().getSimpleName()+" uuid... "+request.getUuid() +" for " + request.getOperation());

        try {
            switch (request.getOperation()){
                case INSERT:
                    userService.create(UserMapper.build(request));
                    break;
                case EDIT:
                    userService.update(UserMapper.build(request));
                    break;
                default:
                    status = STATUS.FAILURE;

            }
            message = "Action successfully";
        }catch (Exception ex){
            status = STATUS.FAILURE;
            message = ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        Response response = Response.newBuilder()
                .setStatus(status)
                .setMessage(message)
                .setObject(request.getClass().getSimpleName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info(">>>>: Status... " + response);
    }

    @Override
    //@Secured(value = "ROLE_PERMISSION_GRPC")
    public void addPermission(PermissionGrpc request, StreamObserver<Response> responseObserver) {

        String message;
        STATUS status = STATUS.SUCCESS;
        log.info(">>>>: Received "+request.getClass().getSimpleName()+" uuid... "+request.getUuid() +" for " + request.getOperation());

        try {
            switch (request.getOperation()){
                case INSERT:
                    permissionService.create(PermissionMapper.build(request));
                   break;
                case EDIT:
                    permissionService.update(PermissionMapper.build(request));
                    break;
                default:
                    status = STATUS.FAILURE;
            }
            message = "Action successfully";
        }catch (Exception ex){
            status = STATUS.FAILURE;
            message = ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        Response response = Response.newBuilder()
                .setStatus(status)
                .setMessage(message)
                .setObject(request.getClass().getSimpleName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

        log.info(">>>>: Status... " + response);
    }

    @Override
    //@Secured(value = "ROLE_CREATE_USER_GRPC")
    public StreamObserver<UserGrpc> createUserStream(StreamObserver<UserList> responseObserver) {

        return new StreamObserver<>() {
            final UserList.Builder accountListBuild = UserList.newBuilder();

            @Override
            public void onNext(UserGrpc userGrpc) {
                User user = UserMapper.build(userGrpc);
                user =  userService.create(user);

                UserGrpc userGrpcCreated = UserMapper.build(user);
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
    //@Secured(value = "ROLE_CREATE_USER_GRPC")
    public StreamObserver<UserGrpc> createUserStreamBidirectional(StreamObserver<UserGrpc> responseObserver) {

        return new StreamObserver<>() {
            int i = 0;
            @Override
            public void onNext(UserGrpc request) {
                User user = UserMapper.build(request);
                user =  userService.create(user);

                UserGrpc reply = UserMapper.build(user);
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