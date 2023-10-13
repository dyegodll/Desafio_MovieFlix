package com.devsuperior.movieflix.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration  //classe de configuração
@EnableResourceServer //implementa a funcionalidade do servidor de recursos do OAuth2 (tipo JWT)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
	private JwtTokenStore tokenStore;
	
	//ambiente de desenvolvimento da aplicação
	@Autowired
	private Environment env;
	
	//enpoints perfil públicos
	//1-tela de login,2-Console H2 Test
	private static final String[] PUBLIC = {"/oauth/token","/h2-console/**"};
	
	//configura a decriptação do token vindo do client, analisando o secret, claims e a validade
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	//QUEM pode acessar O QUE?!
	//configura as rotas dos recursos para liberação por usuário específico
	//o catálogo será liberado para todos, independente de realizar login
	//CRUD dos Produtos e Categoria dependem de login + roles de OPERADOR ou ADMIN atribuídos ao usuário 
	//CRUD de Usuário depende de login + roles de ADMIN atribuídos ao usuário
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//define autorizações das requisições por rota
		http.authorizeRequests()
				.antMatchers(PUBLIC).permitAll() //não exige login, público, rota principal, liberado pra todos os perfis
					.anyRequest().authenticated();
		
		//BANCO-H2 CONSOLE
		//Quais os spring.profiles.active em execução?
		//se detre os ativos existe o perfil teste
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			//desabilite a proteção dos frames dele
			http.headers().frameOptions().disable();
		}
	}
	
}
