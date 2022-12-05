package com.rara.my_selectshop.service;

import com.rara.my_selectshop.dto.LoginRequestDto;
import com.rara.my_selectshop.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	//회원가입
	void signup(SignupRequestDto signupRequestDto);

	//로그인
	void login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse);
}
