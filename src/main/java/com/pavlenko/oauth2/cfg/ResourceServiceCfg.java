package com.pavlenko.oauth2.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServiceCfg extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "oauth-resource";

	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer security) throws Exception {
		security.resourceId(RESOURCE_ID).tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers("/api/**")
				.access("#oauth2.hasScope('trust') or hasRole('ROLE_CLIENT') or hasRole('ROLE_TRUSTED_CLIENT')");
		// @formatter:on
	}
}
