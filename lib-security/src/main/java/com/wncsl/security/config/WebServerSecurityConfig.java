package com.wncsl.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@Configuration
public class WebServerSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Retorna um instancia de PasswordEncoder para ser usado pelo spring security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }

    /**
     * Metodo override de WebSecurityConfigurerAdapter para prover o
     * AuthenticationManager usado no Oauth2
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Configuracoes de recursos estaticos(js, css, imagens, etc.)
     * para serem acessados sem autenticacao
     */
    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring()
                .antMatchers("/**.html",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/configuration/**",
                        "/swagger-resources/**");
    }

}
