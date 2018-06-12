package com.pavlenko.oauth2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pavlenko.oauth2.model.UserAccountDetails;
import com.pavlenko.oauth2.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {
	private final UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return userRepo.findByUsername(username).map(UserAccountDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Account with name " + username + " not found!"));
	}

}
