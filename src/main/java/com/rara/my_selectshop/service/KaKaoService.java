package com.rara.my_selectshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;

public interface KaKaoService {
	String kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException;
}
