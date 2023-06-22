package com.wncsl.auth.config.grpc;


import net.devh.boot.grpc.server.security.authentication.AnonymousAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/*

    This configuration class is an example of authentication on GRPC server using spring security.
    In this project I choose to create a personal kind of authentication.
    Instead of use spring to manager this process I created a field token in each proto object
    and every time a request is received from a client GRPC, the token is validated,
    if not valid the request is refused.

    I commented all Spring annotations and this class is not called on initialization

 */

/*  proxyTargetClass is required, if you use annotation driven security!
    However, you will receive a warning that GrpcServerService#bindService() method is final.
    You cannot avoid that warning (without massive amount of work), but it is safe to ignore it.
    The #bindService() method uses a reference to 'this', which will be used to invoke the methods.
    If the method is not final it will delegate to the original instance and thus it will bypass any security layer that
    you intend to add, unless you re-implement the #bindService() method on the outermost layer (which Spring does not).
*/
@Configuration(proxyBeanMethods = false)
//@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true) //This override the protected methods to provide custom implementations.
public class GrpcServerSecurityConfig {

    /**
     * Configure which authentication types you support.
     * this bear is mandatory when we are using the <grpc-server-spring-boot-starter></grpc-server-spring-boot-starter>
     */
    @Bean
    GrpcAuthenticationReader authenticationReader() {
        return new BasicGrpcAuthenticationReader();
        // final List<GrpcAuthenticationReader> readers = new ArrayList<>();
        // readers.add(new BasicGrpcAuthenticationReader());
        // readers.add(new SSLContextGrpcAuthenticationReader());
        // return new CompositeGrpcAuthenticationReader(readers);
    }

    /**
     * One of your authentication providers.
     * They ensure that the credentials are valid and populate the user's authorities.
     */
//    @Bean
//    DaoAuthenticationProvider daoAuthenticationProvider(final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {
//        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(passwordEncoder);
//        return provider;
//    }
}