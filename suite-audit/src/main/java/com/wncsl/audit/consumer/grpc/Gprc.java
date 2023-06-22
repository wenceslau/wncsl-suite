//package com.wncsl.audit.consumer.grpc;
//
//import com.wncsl.grpc.account.*;
//import io.grpc.stub.StreamObserver;
//import net.devh.boot.grpc.server.service.GrpcService;
//
//
//@GrpcService
//public class Gprc extends AccountServiceGrpc.AccountServiceImplBase {
//
//    public Gprc() {
//    }
//
//    @Override
//    //@Secured(value = "ROLE_USER_GRPC")
//    public void addUser(UserGrpc request, StreamObserver<Response> responseObserver) {
//        System.out.println("addUser");
//        Response response = Response.newBuilder()
//                .setStatus(STATUS.CREATED)
//                .setMessage("addUser")
//                .setObject(request.getClass().getSimpleName())
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    //@Secured(value = "ROLE_PERMISSION_GRPC")
//    public void addPermission(PermissionGrpc request, StreamObserver<Response> responseObserver) {
//        System.out.println("addPermission");
//        Response response = Response.newBuilder()
//                .setStatus(STATUS.CREATED)
//                .setMessage("addPermission")
//                .setObject(request.getClass().getSimpleName())
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void addUserAction(UserActionGrpc request, StreamObserver<Response> responseObserver) {
//        System.out.println("addUserAction");
//        Response response = Response.newBuilder()
//                .setStatus(STATUS.CREATED)
//                .setMessage("addUserAction")
//                .setObject(request.getClass().getSimpleName())
//                .build();
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    //@Secured(value = "ROLE_CREATE_USER_GRPC")
//    public StreamObserver<UserGrpc> createUserStream(StreamObserver<UserList> responseObserver) {
//
//        return new StreamObserver<>() {
//            final UserList.Builder accountListBuild = UserList.newBuilder();
//
//            @Override
//            public void onNext(UserGrpc userGrpc) {
//                System.out.println("onNext");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//                responseObserver.onNext(accountListBuild.build());
//                responseObserver.onCompleted();
//            }
//        };
//    }
//
//
//
//    @Override
//    //@Secured(value = "ROLE_CREATE_USER_GRPC")
//    public StreamObserver<UserGrpc> createUserStreamBidirectional(StreamObserver<UserGrpc> responseObserver) {
//
//        return new StreamObserver<>() {
//            int i = 0;
//            @Override
//            public void onNext(UserGrpc request) {
//                System.out.println("onNext");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("onError");
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("onCompleted");
//                responseObserver.onCompleted();
//            }
//        };
//
//    }
//}
