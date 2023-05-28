package com.wncsl.auth.consumer.grpc;

import com.wncsl.auth.domain.permission.PermissionMapper;
import com.wncsl.auth.domain.permission.PermissionService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserMapper;
import com.wncsl.auth.domain.user.UserService;
import com.wncsl.grpc.code.PermissionGrpc;
import com.wncsl.grpc.code.UserGrpc;
import com.wncsl.grpc.code.AccountServiceGrpc;

import com.wncsl.grpc.code.UserList;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;

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
    @Secured(value = "ROLE_CREATE_USER_GRPC")
    public void createUser(UserGrpc request, StreamObserver<UserGrpc> responseObserver) {

        String status = "CREATED";
        log.info(">>>>: Creating user uuid... "+request.getUuid());

        try {
            userService.create(UserMapper.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        UserGrpc reply = UserMapper.clone(request, status);

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info(">>>>: Status created user... " + status);
    }

    @Override
    public void updateUser(UserGrpc request, StreamObserver<UserGrpc> responseObserver) {

        String status = "UPDATED";
        log.info(">>>>: Updating user uuid... "+request.getUuid());

        try {
            userService.update(UserMapper.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        UserGrpc reply = UserMapper.clone(request, status);

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info(">>>>: Status updated user... " + status);

    }

    @Override
    public void createPermission(PermissionGrpc request, StreamObserver<PermissionGrpc> responseObserver) {

        String status = "CREATED";
        log.info(">>>>: Creating permission uuid... "+request.getUuid());

        try {
           permissionService.create(PermissionMapper.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        PermissionGrpc reply = PermissionMapper.clone(request, status);

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info(">>>>: Status created permission... " + status);

    }

    @Override
    public void updatePermission(PermissionGrpc request, StreamObserver<PermissionGrpc> responseObserver) {
        String status = "UPDATED";
        log.info(">>>>: Updating permission uuid... "+request.getUuid());

        try {
            permissionService.update(PermissionMapper.build(request));
        }catch (Exception ex){
            status = "ERROR:"+ex.getMessage();
            log.error(ex.getMessage(),ex);
        }

        PermissionGrpc reply = PermissionMapper.clone(request, status);

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        log.info(">>>>: Status updated permission... " + status);
    }

    @Override
    @Secured(value = "ROLE_CREATE_USER_GRPC")
    public StreamObserver<UserGrpc> createUserStream(StreamObserver<UserList> responseObserver) {

        return new StreamObserver<>() {
            final UserList.Builder accountListBuild = UserList.newBuilder();

            @Override
            public void onNext(UserGrpc userGrpc) {
                User user = UserMapper.build(userGrpc);
                user =  userService.create(user);

                UserGrpc userGrpcCreated = UserMapper.build(user, "CREATED");
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
                User user = UserMapper.build(request);
                user =  userService.create(user);

                UserGrpc reply = UserMapper.build(user, "CREATED");
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