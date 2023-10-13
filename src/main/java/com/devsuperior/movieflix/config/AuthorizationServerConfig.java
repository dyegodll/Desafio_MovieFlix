package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.devsuperior.movieflix.components.JwtTokenEnhancer;

@Configuration // informa que a classe é de configuração
@EnableAuthorizationServer //implementa a funcionalidade do AuthorizationServer do OAuth2 (tipo JWT)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clienSecret;
	
	@Value("${jwt.duration}")
	private Integer jwtDuration;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	//usado para inserir informações adicionais no token
	@Autowired
	private JwtTokenEnhancer tokenEnhancer;
	
	//tbm usado para inserir informações adicionais no token
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	//configura o Token para o AuthorizationServer
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	//define autenticação e os dados do cliente(aplicação/app) - credenciais da aplicação
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() //define processo em memória
		.withClient(clientId) //define o id/nome do app que será usado pela aplicação web(LOGIN da APLICAÇÃO no Servidor) para acessar o backend | OBS.: NÃO LOGIN DO USUÁRIO
		.secret(passwordEncoder.encode(clienSecret)) //senha de acesso a APLICAÇÃO já CRIPTOGRAFADA | OBS.: NÃO SENHA DO USUÁRIO
		.scopes("read", "write") //acesso de leitura e escrita
		.authorizedGrantTypes("password", "refresh_token") //tipo de acesso/login
		.accessTokenValiditySeconds(jwtDuration) //tempo de validação do token em segundos (nesse caso 24h)
		.refreshTokenValiditySeconds(jwtDuration); // 86400 segundos de token válido
	}

	//define quem vai autorizar e qual vai ser o formato do token (JWT)
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//configura o aprimorador do token(TokenEnhancer) para adicionar informações a ele
		TokenEnhancerChain chain = new TokenEnhancerChain();
		chain.setTokenEnhancers(Arrays.asList(tokenEnhancer,accessTokenConverter)); //exige uma lista com o TokenEnhancer + JwtAccessTokenConverter
		
		endpoints.authenticationManager(authenticationManager) //define o AuthenticationManager que processará a autenticação
		.tokenStore(tokenStore) //processa o token
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(chain); //define o TokenEnhancer
	}

}
