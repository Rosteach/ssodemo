package com.pavlenko.oauth2.entity.projection;

import java.util.List;
import java.util.stream.Collectors;

import com.pavlenko.oauth2.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProjection {
	private Long id;
	private String username;
	private String password;
	private boolean active;
	private List<RoleProjection> roles;
	private List<UserPropertyProjection> properties;

	public UserProjection(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.active = user.isActive();
		this.roles = user.getRoles().stream().map(RoleProjection::new).collect(Collectors.toList());
		this.properties = user.getProperties().stream().map(UserPropertyProjection::new).collect(Collectors.toList());
	}
}
