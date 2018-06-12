package com.pavlenko.oauth2.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	private boolean active;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UserRole> roles;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = UserProperty.class, orphanRemoval = true)
	private List<UserProperty> properties;

	public User(String username, String password, boolean active, List<UserRole> roles) {
		this.username = username;
		this.password = password;
		this.active = active;
		this.roles = roles;
	}

	public void add(UserProperty prop) {
		if(this.properties == null)
			this.properties = new ArrayList<>();
		
		this.properties.add(prop);
	}
}
