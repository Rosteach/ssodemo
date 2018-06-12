package com.pavlenko.oauth2.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServiceCfg extends AuthorizationServerConfigurerAdapter {
	private final ClientDetailsService clientDetailsService;

	public AuthServiceCfg(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
		clients.inMemory()
			.withClient("basic-client")
				.authorizedGrantTypes("client_credentials")
				.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
				.scopes("trust")
				.secret("secret");
		// @formatter:on
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
				.tokenStore(tokenStore())
				.userApprovalHandler(userApprovalHandler())
				.accessTokenConverter(accessTokenConverter());
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserApprovalHandler userApprovalHandler() {
		ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
		userApprovalHandler.setApprovalStore(approvalStore());
		userApprovalHandler.setClientDetailsService(clientDetailsService);
		userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		return userApprovalHandler;
	}

	@Bean
	public TokenStore tokenStore() {
		JwtTokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
		tokenStore.setApprovalStore(approvalStore());
		return tokenStore;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		return new JwtAccessTokenConverter();
	}

	@Bean
	public ApprovalStore approvalStore() {
		return new InMemoryApprovalStore();
	}
}
