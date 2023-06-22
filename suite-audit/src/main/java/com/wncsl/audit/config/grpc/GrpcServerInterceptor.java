//package com.wncsl.audit.config.grpc;
//
//import io.grpc.Metadata;
//import io.grpc.ServerCall;
//import io.grpc.ServerCall.Listener;
//import io.grpc.ServerCallHandler;
//import io.grpc.ServerInterceptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class GrpcServerInterceptor implements ServerInterceptor {
//
//    private static final Logger log = LoggerFactory.getLogger(GrpcServerInterceptor.class);
//
//    @Override
//    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
//                                                      ServerCallHandler<ReqT, RespT> serverCallHandler) {
//
//        log.info("getFullMethodName: "+ serverCall.getMethodDescriptor().getFullMethodName());
//        log.info("getSecurityLevel: "+serverCall.getSecurityLevel() + "");
//        log.info("getAttributes: "+serverCall.getAttributes()+ "");
//        log.info("keys: "+metadata.keys()+ "");
//        log.info("getAuthority: "+serverCall.getAuthority());
//
//        return serverCallHandler.startCall(serverCall, metadata);
//    }
//
//}