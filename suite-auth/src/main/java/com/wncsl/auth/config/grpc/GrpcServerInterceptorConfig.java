package com.wncsl.auth.config.grpc;

import com.wncsl.auth.grpc.GrpcServerInterceptor;
import org.springframework.context.annotation.Configuration;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

@Configuration(proxyBeanMethods = false)
public class GrpcServerInterceptorConfig {

    @GrpcGlobalServerInterceptor
    GrpcServerInterceptor logServerInterceptor() {
        return new GrpcServerInterceptor();
    }

}