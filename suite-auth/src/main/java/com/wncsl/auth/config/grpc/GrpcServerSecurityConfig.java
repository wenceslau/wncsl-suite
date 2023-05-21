package com.wncsl.auth.config.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


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

    // This could be your database lookup. There are some complete implementations in spring-security-web.
    //@Bean
//    UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
//        return username -> {
//            //log.info("Searching user: {}", username);
//            switch (username) {
//                case "guest": {
//                    return new User(username, passwordEncoder.encode(username + "Password"), Collections.emptyList());
//                }
//                case "user": {
//                    log.info("user found!");
//                    //The SimpleGrantedAuthority must have to start with ROLE, mandatory
//                    final List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_CREATE_ACCOUNT"));
//                    return new User(username, passwordEncoder.encode(username + "Password"), authorities);
//                }
//                default: {
//                    throw new UsernameNotFoundException("Could not find user!");
//                }
//            }
//        };
//    }

    // Add the authentication providers to the manager.
//    @Bean
//    AuthenticationManager authenticationManager(final DaoAuthenticationProvider daoAuthenticationProvider) {
//        final List<AuthenticationProvider> providers = new ArrayList<>();
//        providers.add(daoAuthenticationProvider);
//        return new ProviderManager(providers);
//    }
}