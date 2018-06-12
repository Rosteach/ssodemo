package com.pavlenko.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GlobalViewController {

	@GetMapping(value = "/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login(@RequestParam(name = "login-error", required = false) boolean error, Model model) {
		model.addAttribute("loginError", error);
		return "login";
	}
}
