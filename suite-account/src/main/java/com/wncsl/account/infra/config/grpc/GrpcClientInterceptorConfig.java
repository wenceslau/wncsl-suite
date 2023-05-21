package com.wncsl.account.infra.config.grpc;

import com.wncsl.account.infra.grpc.GrpcClientInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor;

@Order(Ordered.LOWEST_PRECEDENCE)
@Configuration(proxyBeanMethods = false)
public class GrpcClientInterceptorConfig {

    @GrpcGlobalClientInterceptor
    GrpcClientInterceptor logClientInterceptor() {
        return new GrpcClientInterceptor();
    }

}