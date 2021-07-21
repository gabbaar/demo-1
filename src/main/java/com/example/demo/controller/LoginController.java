package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	// @GetMapping("/login")
	// public String getUserLogin() {
	// return "login";
	// }
	@GetMapping("/home")
	public String getHome() {
		return "home";

	}
}
