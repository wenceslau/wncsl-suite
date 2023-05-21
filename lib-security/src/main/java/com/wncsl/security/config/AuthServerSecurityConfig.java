package com.wncsl.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.time.LocalTime;

/**
 * This class provide HTTP Basic authentication for endpoint /oauth/token
 * It means that you need a simple user and password for require this endpoint
 * even if you have a valid user for application
 */
@Configuration
public class AuthServerSecurityConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Metodo que faz o join entre o OAuth2 e Spring Security
	 * Configura olguns parametros para o token
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// Adiciona o token ao authenticationManager
		endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		System.out.println(this.getClass().getSimpleName()+".accessTokenConverter: "+ LocalTime.now());
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("cdpgto");
		return accessTokenConverter;
	}

	/**
	 * Metodo tb chamado durante o start da aplicacao, pelo Spring
	 * Cria uma "Store" uma loja para tokens JWT
	 */
	@Bean
	public TokenStore tokenStore() {
		System.out.println(this.getClass().getSimpleName()+".tokenStore: "+ LocalTime.now());
		return new JwtTokenStore(accessTokenConverter());
	}


}
