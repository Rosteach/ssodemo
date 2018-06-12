package com.pavlenko.oauth2.entity.projection;

import com.pavlenko.oauth2.entity.Role;
import com.pavlenko.oauth2.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleProjection {
	private Long id;
	private Role role;

	public RoleProjection(UserRole userRole) {
		this.id = userRole.getId();
		this.role = userRole.getName();
	}
}
