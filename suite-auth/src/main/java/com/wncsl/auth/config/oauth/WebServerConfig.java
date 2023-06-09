package com.wncsl.auth.config.oauth;

import com.wncsl.security.config.WebServerSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@Configuration
@EnableWebSecurity          //Desliga todas as config default do spring security
@EnableResourceServer       //This annotation provide access path that are protected by OAuth2
@EnableAuthorizationServer  //This annotation provide http basic authentication for end point /oauth/token
public class WebServerConfig extends WebServerSecurityConfig {


    /**
     * Retorna um instancia de PasswordEncoder para ser usado pelo spring security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return super.passwordEncoder();
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
        super.configure(webSecurity);
    }

}
