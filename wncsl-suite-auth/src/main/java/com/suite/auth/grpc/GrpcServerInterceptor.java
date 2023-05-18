package com.suite.auth.grpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class GrpcServerInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerInterceptor.class);

    @Override
    public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
                                                      ServerCallHandler<ReqT, RespT> serverCallHandler) {

        log.info(serverCall.getMethodDescriptor().getFullMethodName());
        System.out.println(serverCall.getMethodDescriptor().getServiceName());
        System.out.println(serverCall.getSecurityLevel());
        System.out.println(serverCall.getAttributes());
        System.out.println(metadata.keys());

        return serverCallHandler.startCall(serverCall, metadata);
    }

}