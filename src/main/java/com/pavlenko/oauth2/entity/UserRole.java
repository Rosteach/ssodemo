package com.pavlenko.oauth2.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pavlenko.oauth2.entity.projection.RoleProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole {
	@Id
	@GeneratedValue
	private Long id;
	@Enumerated(EnumType.STRING)
	private Role name;

	public UserRole(Role role) {
		this.name = role;
	}

	public UserRole(RoleProjection role) {
		this.name = role.getRole();
	}
}
