package com.pavlenko.oauth2.controller.mav;

public enum ViewProps {
	MESSAGE,
	
	USERS, 
	
	USER_PROPERTIES("userprops");

	private String value;

	private ViewProps() {
	}

	private ViewProps(String value) {
		this.value = value;
	}

	public String value() {
		return this.value == null ? this.name().toLowerCase() : this.value;
	}
}
