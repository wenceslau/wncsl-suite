//package com.wncsl.audit.config.grpc;
//
//import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration(proxyBeanMethods = false)
//public class GrpcServerInterceptorConfig {
//
//    @GrpcGlobalServerInterceptor
//    GrpcServerInterceptor logServerInterceptor() {
//        return new GrpcServerInterceptor();
//    }
//
//}