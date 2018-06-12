package com.pavlenko.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pavlenko.oauth2.controller.mav.ModelAndViewProvider;

@Controller
@RequestMapping("/users")
public class UserViewController {
	private final ModelAndViewProvider provider;

	public UserViewController(ModelAndViewProvider provider) {
		this.provider = provider;
	}

	@GetMapping
	public ModelAndView users() {
		return provider.createUsersPage();
	}

	@GetMapping("/{id}/properties")
	public ModelAndView userProps(@PathVariable Long id) {
		return provider.createUserPropsPage(id);
	}
}
