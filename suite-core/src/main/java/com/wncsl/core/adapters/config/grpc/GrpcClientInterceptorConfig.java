package com.wncsl.core.adapters.config.grpc;

import com.wncsl.core.adapters.outbound.grpc.GrpcClientInterceptor;
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