package com.wncsl.auth.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

import java.time.LocalTime;

/**
 * Use this to configure the access rules for secure resources. By default, all resources not in "/oauth/**" are protected
 * (but no specific rules about scopes are given, for instance). You also get an OAuth2WebSecurityExpressionHandler by default.
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    //URI`s que poderam ser acessados sem autenticação
    private static final String[] AUTH_WHITELIST = {"/auth/**", "/health", "/oauth/**" };

    /**
     * Metodo chamado apos o AutorizationServerConfig.configure(ClientDetailsServiceConfigurer)
     * Adiciona true para stateless, mantem sem estado
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.stateless(true);
    }

    /**
     * Metodo é chamado apos o ResourceServerConfig.configure(ResourceServerSecurityConfigurer)
     * Define as propriedades do HttpSecurity, que faz a autenticacao para oauth2
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated() // Qualquer requsisicao tem q autenticar
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // mesma funcao do metodo
                .and()
                .csrf().disable()
                .headers().frameOptions().disable(); // ??
    }

    /**
     * ??
     * @return
     */
    @Bean
    public MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }
}
