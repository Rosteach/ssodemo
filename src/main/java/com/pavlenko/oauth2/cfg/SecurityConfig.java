package com.pavlenko.oauth2.cfg;

import java.util.Collections;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pavlenko.oauth2.entity.Role;
import com.pavlenko.oauth2.entity.User;
import com.pavlenko.oauth2.entity.UserRole;
import com.pavlenko.oauth2.model.UserAccountDetails;
import com.pavlenko.oauth2.repository.UserRepository;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final UserRepository userRepo;
	private final PasswordEncoder passEncoder;
	
	/**
	 * configure web security to ignore static resources
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css**", "/js**", "/material**", "/webjars**");
	}

	/**
	 * configure basic http security request access
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// configure access levels
		http.csrf().disable()
				.authorizeRequests()
					.antMatchers("/")
						.permitAll()
					.antMatchers("/users**","/api**")
						.authenticated();

		// configure login paths
		http
			.formLogin()
				.loginPage("/login").failureUrl("/login?login-error=true")
			.and()
			    .logout().logoutSuccessUrl("/");
	}

	/**
	 * configure global authentication manager with custom user details service
	 * implementation and password encoder
	 * 
	 * causes default users creation
	 * 
	 * @param auth
	 * @param root
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// check if user storage is absent and create root user
		if (userRepo.count() == 0)
			Stream.of("root,ACTUATOR", "user,USER").map(arr -> arr.split(","))
					.forEach(arr -> userRepo.save(new User(arr[0], passEncoder.encode(arr[0]), true,
							Collections.singletonList(new UserRole(Role.valueOf(arr[1]))))));

		auth.userDetailsService(createDetailsService()).passwordEncoder(passEncoder);
	}

	private UserDetailsService createDetailsService() {
		return username -> userRepo.findByUsername(username).map(UserAccountDetails::new).orElseThrow(
				() -> new UsernameNotFoundException("Principal with name " + username + " does not exist!"));
	}
}
