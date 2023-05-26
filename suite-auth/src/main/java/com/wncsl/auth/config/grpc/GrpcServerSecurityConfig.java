package com.wncsl.auth.config.grpc;

import net.devh.boot.grpc.server.security.authentication.AnonymousAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// proxyTargetClass is required, if you use annotation driven security!
// However, you will receive a warning that GrpcServerService#bindService() method is final.
// You cannot avoid that warning (without massive amount of work), but it is safe to ignore it.
// The #bindService() method uses a reference to 'this', which will be used to invoke the methods.
// If the method is not final it will delegate to the original instance and thus it will bypass any security layer that
// you intend to add, unless you re-implement the #bindService() method on the outermost layer (which Spring does not).
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true) //This override the protected methods to provide custom implementations.
public class GrpcServerSecurityConfig {

    /**
     * Configure which authentication types you support.
     * @return
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
     * @param userDetailsService
     * @param passwordEncoder
     * @return
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}