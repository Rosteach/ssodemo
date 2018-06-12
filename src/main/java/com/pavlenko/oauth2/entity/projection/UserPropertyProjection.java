package com.pavlenko.oauth2.entity.projection;

import com.pavlenko.oauth2.entity.UserProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPropertyProjection {
	private Long id;
	private String name;
	private String value;

	public UserPropertyProjection(UserProperty userProperty) {
		this.id = userProperty.getId();
		this.name = userProperty.getName();
		this.value = userProperty.getValue();
	}
}
