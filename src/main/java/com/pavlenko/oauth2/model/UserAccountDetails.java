package com.pavlenko.oauth2.model;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pavlenko.oauth2.entity.User;

public class UserAccountDetails implements UserDetails {

	private static final long serialVersionUID = 3691137588689701153L;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public UserAccountDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
