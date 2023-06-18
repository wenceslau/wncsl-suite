package com.wncsl.core.adapters.config.grpc;

import org.springframework.beans.factory.annotation.Value;

import io.grpc.CallCredentials;
import net.devh.boot.grpc.client.inject.StubTransformer;
import net.devh.boot.grpc.client.security.CallCredentialsHelper;

/*

    This configuration class is an example of authentication on GRPC server using spring security.
    In this project I choose to create a personal kind of authentication.
    Instead of use spring to manager this process I created a field token in each proto object
    and every time a request is sent to GRPC Server, the token is sent ,
    if not valid the GRPC service will refuse the request.

    I commented all Spring annotations and this class is not called on initialization

 */

/**
 * The security configuration for the client. In this case we assume that we use the same passwords for all stubs. If
 * you need per stub credentials you can delete the grpcCredentials and define a {@link StubTransformer} yourself.
 *
 * @author Daniel Theuke (daniel.theuke@heuboe.de)
 * @see CallCredentialsHelper
 */
//@Configuration(proxyBeanMethods = false)
public class GrpcClientSecurityConfig {

    @Value("${grpc.auth.username}")
    private String username;

    @Value("${grpc.auth.password}")
    private String password;

    // Create credentials for username + password.
    //@Bean
    CallCredentials grpcCredentials() {
        System.out.println("username gPRC Server: " + username);
        return CallCredentialsHelper.basicAuth(username, password);
    }

}