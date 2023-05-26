package com.wncsl.auth.config.oauth;

import com.wncsl.security.config.AuthServerSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.util.Arrays;

/**
 * This class provide HTTP Basic authentication for endpoint /oauth/token
 * It means that you need a simple user and password for require this endpoint
 * even if you have a valid user for application
 */
@Configuration
public class AuthServerConfig extends AuthServerSecurityConfig {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private HttpServletRequest request;

	////////////METODOS CONFIG

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

	////////////METODOS BEANS SPRING

	/**
	 * Disponibiliza uma instancia do CustomTokenEnhancer
	 * Casse do modulo que customiza o conteudo do token
	 */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		System.out.println(this.getClass().getSimpleName()+".tokenEnhancer: "+ LocalTime.now());
		return new CustomTokenService();
	}

	////////////EVENTOS

	/**
	 * Evento dispara quando ha sucesso na autenticacao, via user ou token
	 */
	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
		System.out.println(this.getClass().getSimpleName()+".authSuccessEventListener: "+ LocalTime.now());

		Object source = authorizedEvent.getSource();
		System.out.println("InstanceOf = " + source.getClass().getName());

		if (source instanceof OAuth2Authentication) {
			//é uma autenticao com o token
			OAuth2Authentication ase = (OAuth2Authentication) source;
			System.err.println("Name "+  ase.getName());
		} else if (source instanceof UsernamePasswordAuthenticationToken) {
			//é uma autenticacao com usuario, seja o user da APP, ou o user final
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) source;
			if ("web".equals(upat.getName()) || "mobile".equals(upat.getName())) {
				System.err.println("SUCESS APP WEB OU MOBILE " + upat.getName());
			} else if ("access-token".equals(upat.getName())) {
				System.err.println("SUCESS ACCESS USING TOKEN " + upat.getName());
			} else {
				//TODO colocar audit logon de sucess aqui
				System.err.println("SUCESS USER REQUEST TOKEN " + upat.getName());
			}
		}
	}

	/**
	 * Evento dispara quando ocorre falha na autenticacao
	 */
	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent) {
		System.out.println(this.getClass().getSimpleName()+".authFailedEventListener: "+ LocalTime.now());

		Object source = oAuth2AuthenticationFailureEvent.getSource();
		System.out.println("InstanceOf = " + source.getClass().getName());

		if (source instanceof OAuth2Authentication) {
			//é uma autenticao com o token
			OAuth2Authentication ase = (OAuth2Authentication) source;
			System.err.println("Name "+  ase.getName());


		} else if (source instanceof UsernamePasswordAuthenticationToken) {
			//é uma autenticacao com usuario, seja o user da APP, ou o user final
			UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken) source;
			if ("web".equals(upat.getName()) || "mobile".equals(upat.getName())) {
				//Auth app web ou mobile
				System.err.println("FAILURE APP WEB OU MOBILE " + upat.getName());
			} else if ("access-token".equals(upat.getName())) {
				System.err.println("FAILURE ACCESS USING TOKEN " + upat.getName());
			} else {
				//TODO colocar audit logon de pass errada aqui
				System.err.println("FAILURE USER REQUEST TOKEN " + upat.getName() );
			}
		} else if (source instanceof PreAuthenticatedAuthenticationToken) {
			//é uma autenticacao com usuario, seja o user da APP, ou o user final
			PreAuthenticatedAuthenticationToken paat = (PreAuthenticatedAuthenticationToken) source;
			if ("web".equals(paat.getName()) || "mobile".equals(paat.getName())) {
				//Auth app web ou mobile
				System.err.println("FAILURE APP WEB OU MOBILE " + paat.getName());
			} else if ("access-token".equals(paat.getName())) {
				System.err.println("FAILURE ACCESS USING TOKEN " + paat.getName());
			} else {
				System.err.println("FAILURE USER REQUEST TOKEN " + paat.getName() );
			}
		}
	}

}
