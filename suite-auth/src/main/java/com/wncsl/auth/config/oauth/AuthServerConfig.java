package com.wncsl.auth.config.oauth;

import com.wncsl.auth.consumer.grpc.GrpcServerInterceptor;
import com.wncsl.auth.domain.logonhistory.LogonHistory;
import com.wncsl.auth.domain.logonhistory.LogonHistoryService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.security.CustomUser;
import com.wncsl.security.config.AuthServerSecurityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * This class provide HTTP Basic authentication for endpoint /oauth/token
 * It means that you need a simple user and password for require this endpoint
 * even if you have a valid user for application
 */
@Configuration
public class AuthServerConfig extends AuthServerSecurityConfig {
	private static final Logger log = LoggerFactory.getLogger(GrpcServerInterceptor.class);

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private LogonHistoryService logonHistoryService;


	/**
	 * Metodo que faz o join entre o OAuth2 e Spring Security
	 * Configura olguns parametros para o token
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		System.out.println(this.getClass().getSimpleName()+".configure(AuthorizationServerEndpointsConfigurer): "+ LocalTime.now());

		// Objects para manipular o conteudo do token. CustomTokenEnhancer adiciona detalhes ao token
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));

		endpoints.tokenStore(tokenStore())  // Armazena o toke em uma "loja", as requisicoes validam o token nessa Store
				 .tokenEnhancer(tokenEnhancerChain) // O token é gerado e tem dados adicionais // setado pelo Enhancer criado acima
				 .reuseRefreshTokens(false) // ??
				 .authenticationManager(authenticationManager) // Adiciona o token ao authenticationManager
				 .userDetailsService(userDetailsService); //Usa a classe Details Server para validar o user
	}

	/**
	 * Metodo para autenticaçao do cliente da API, apps, web, outras api
	 * Indica que para acessar o endPoint /oauth/token precisa estar autenticado
	 * os dados são definidos aqui
	 * Define confituracoes do token
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		System.out.println(this.getClass().getSimpleName()+".configure(ClientDetailsServiceConfigurer): "+ LocalTime.now());

		clients.inMemory()
			.withClient("web")
			.secret(passwordEncoder.encode("@web123"))
			.authorizedGrantTypes("password")
			.scopes("read", "write", "delete")
			.accessTokenValiditySeconds(3600 * 3) // 3600 = 1 hora
			.and()
			.withClient("mobile")
			.secret(passwordEncoder.encode("@mobile123"))
			.authorizedGrantTypes("password")
			.scopes("read", "write")
			.accessTokenValiditySeconds(3600 * 3); // 3600 = 1 hora
	}

	/**
	 * Disponibiliza uma instancia do CustomTokenEnhancer
	 * Casse do modulo que customiza o conteudo do token
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		System.out.println(this.getClass().getSimpleName()+".tokenEnhancer: "+ LocalTime.now());
		return new CustomTokenService();
	}

	/**
	 * Evento dispara quando ha sucesso na autenticacao, via user ou token
	 */
	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent authenticationSuccessEvent) {

		Object source = authenticationSuccessEvent.getSource();
		log.info("InstanceOf AuthenticationSuccessEvent.getSource() :.." + source.getClass().getName());

		if (source instanceof OAuth2Authentication) {
			OAuth2Authentication oa2a = (OAuth2Authentication) source;
			log.info("EXECUTED WHEN A RESOURCE IS ACCESSED USING A VALID TOKEN. upat.getName():.." + oa2a.getName());
			log.info("upat principal:.." + oa2a.getPrincipal());

		} else if (source instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) source;
			log.info("EXECUTED WHEN USER REQUEST A TOKEN, FIRST THE WEB CLIENT AND AFTER THE USER CLIENT. upat.getName():.." + upat.getName() );
			log.info("upat principal:.." + upat.getPrincipal());

			if (upat.getPrincipal() instanceof  CustomUser){
				CustomUser cUsr = (CustomUser) upat.getPrincipal();
				if (!"GRPC".equals(cUsr.getType())) {
					logonHistoryService.auditLogon(cUsr.getUsername(), cUsr.getUserUuid(), "SUCCESS");
				}

			}
		}
	}

	/**
	 * Evento dispara quando ocorre falha na autenticacao
	 */
	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent authenticationFailureEvent) {

		String username = "";
		Object source = authenticationFailureEvent.getSource();
		log.info("InstanceOf AuthenticationFailureEvent.getSource() :.." + source.getClass().getName());
		log.info("Exception().getMessage():.." + authenticationFailureEvent.getException().getMessage());

		if (authenticationFailureEvent.getAuthentication() != null) {
			username =  authenticationFailureEvent.getAuthentication().getName();
		}

		if (source instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) source;
			username = upat.getName();
			log.info("EXECUTED WHEN USER OR PASS IS INVALID. upat.getName():.." + username);
			log.info("upat principal:.." + upat.getPrincipal());

		} else if (source instanceof PreAuthenticatedAuthenticationToken) {
			PreAuthenticatedAuthenticationToken paat = (PreAuthenticatedAuthenticationToken) source;
			username = paat.getName();
			log.info("EXECUTED WHEN TOKE IS INVALID OR EXPIRED. paat.getName():.." + username);
			log.info("paat principal:.." + paat.getPrincipal());
		}

		logonHistoryService.auditLogon(username, null, "FAILURE");
	}

}
