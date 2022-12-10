package com.rara.my_selectshop.controller;

import com.rara.my_selectshop.dto.LoginRequestDto;
import com.rara.my_selectshop.dto.SignupRequestDto;
import com.rara.my_selectshop.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/signup")
	public ModelAndView signupPage() {
		return new ModelAndView("signup");
	}

	@GetMapping("/login-page")
	public ModelAndView loginPage() {
		return new ModelAndView("login");
	}

	@PostMapping("/signup")
	public String signup(SignupRequestDto signupRequestDto) {
		userService.signup(signupRequestDto);
		return "redirect:/api/user/login-page";
	}

	@ResponseBody
	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto loginRequestDto,
		HttpServletResponse response) {
		userService.login(loginRequestDto, response);
		return "success";
	}

	@GetMapping("/forbidden")
	public ModelAndView getForbidden() {
		return new ModelAndView("forbidden");
	}

	@PostMapping("/forbidden")
	public ModelAndView postForbidden() {
		return new ModelAndView("forbidden");
	}
}
