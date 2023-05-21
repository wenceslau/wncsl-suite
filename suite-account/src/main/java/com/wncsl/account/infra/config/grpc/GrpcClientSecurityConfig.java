package com.wncsl.account.infra.config.grpc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.CallCredentials;
import net.devh.boot.grpc.client.inject.StubTransformer;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;

/**
 * The security configuration for the client. In this case we assume that we use the same passwords for all stubs. If
 * you need per stub credentials you can delete the grpcCredentials and define a {@link StubTransformer} yourself.
 *
 * @author Daniel Theuke (daniel.theuke@heuboe.de)
 * @see CallCredentialsHelper
 */
@Configuration(proxyBeanMethods = false)
public class GrpcClientSecurityConfig {

    @Value("${auth.username}")
    private String username;

    // Create credentials for username + password.
    @Bean
    CallCredentials grpcCredentials() {
        System.out.println(username);
        return CallCredentialsHelper.basicAuth(this.username, this.username + "Password");
    }

}