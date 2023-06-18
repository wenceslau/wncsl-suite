package com.wncsl.audit.config;

import com.wncsl.security.config.AuthServerSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * This class provide HTTP Basic authentication for endpoint /oauth/token
 * It means that you need a simple user and password for require this endpoint
 * even if you have a valid user for application
 */
@Configuration
public class AuthServerConfig extends AuthServerSecurityConfig {

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		super.configure(endpoints);
	}

	@Bean
	@Override
	public JwtAccessTokenConverter accessTokenConverter() {
		return super.accessTokenConverter();
	}

	@Bean
	public TokenStore tokenStore() {
		return super.tokenStore();
	}
}
