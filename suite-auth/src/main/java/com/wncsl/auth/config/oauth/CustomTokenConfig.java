package com.wncsl.auth.config.oauth;

import com.wncsl.security.CustomUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe que permite potencializar o token JWT, Permite adicionar informacoes
 * adicional ao token
 *
 * @author Wenceslau Neto
 *
 */
@Component
public class CustomTokenConfig implements TokenEnhancer {


	/**
	 * Metodo usuado para adicionar diversos detalhes no token
	 * Recebe o token padrao ja autenticado e adiciona informacoes relevantes
	 */
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accesToken, OAuth2Authentication authentication) {
		System.out.println(this.getClass().getSimpleName()+": "+ LocalTime.now());

		// Objects UserAccount, extenção do Objects User do Spring que implement o UserDetail, que
		// esta dentro do UserDetailService no caso implementada pela classe UserAccountDatailService
		CustomUser userCustomSystem = (CustomUser) authentication.getPrincipal();

		// Adiciona o informacao adicional em um map para adicionar e depois adiciona ao token
		Map<String, Object> addInfo = new HashMap<>();

		addInfo.put("user_uid", userCustomSystem.getUserUuid());

		((DefaultOAuth2AccessToken) accesToken).setAdditionalInformation(addInfo);

		return accesToken;
	}

}