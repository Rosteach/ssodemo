package com.pavlenko.oauth2.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pavlenko.oauth2.entity.projection.UserPropertyProjection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_property")
public class UserProperty {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String value;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public UserProperty(UserPropertyProjection uProjection) {
		this.name = uProjection.getName();
		this.value = uProjection.getValue();
	}
}
