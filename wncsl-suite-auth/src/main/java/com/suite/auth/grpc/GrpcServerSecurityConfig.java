package com.suite.auth.grpc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;


// proxyTargetClass is required, if you use annotation driven security!
// However, you will receive a warning that GrpcServerService#bindService() method is final.
// You cannot avoid that warning (without massive amount of work), but it is safe to ignore it.
// The #bindService() method uses a reference to 'this', which will be used to invoke the methods.
// If the method is not final it will delegate to the original instance and thus it will bypass any security layer that
// you intend to add, unless you re-implement the #bindService() method on the outermost layer (which Spring does not).
@Configuration(proxyBeanMethods = false)
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class GrpcServerSecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(GrpcServerSecurityConfig.class);

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // This could be your database lookup. There are some complete implementations in spring-security-web.
    @Bean
    UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
        return username -> {
            log.info("Searching user: {}", username);
            switch (username) {
                case "guest": {
                    return new User(username, passwordEncoder.encode(username + "Password"), Collections.emptyList());
                }
                case "user": {
                    log.info("user found!");
                    //The SimpleGrantedAuthority must have to start with ROLE, mandatory
                    final List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_CREATE_ACCOUNT"));
                    return new User(username, passwordEncoder.encode(username + "Password"), authorities);
                }
                default: {
                    throw new UsernameNotFoundException("Could not find user!");
                }
            }
        };
    }


    // One of your authentication providers.
    // They ensure that the credentials are valid and populate the user's authorities.
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(final UserDetailsService userDetailsService, final PasswordEncoder passwordEncoder) {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // Add the authentication providers to the manager.
    @Bean
    AuthenticationManager authenticationManager(final DaoAuthenticationProvider daoAuthenticationProvider) {
        final List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    // Configure which authentication types you support.
    @Bean
    GrpcAuthenticationReader authenticationReader() {
        return new BasicGrpcAuthenticationReader();
        // final List<GrpcAuthenticationReader> readers = new ArrayList<>();
        // readers.add(new BasicGrpcAuthenticationReader());
        // readers.add(new SSLContextGrpcAuthenticationReader());
        // return new CompositeGrpcAuthenticationReader(readers);
    }
}