package com.rara.my_selectshop.controller;

import com.rara.my_selectshop.entity.ApiUseTime;
import com.rara.my_selectshop.entity.UserRoleEnum;
import com.rara.my_selectshop.repository.ApiUseTimeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiUseTimeController {

	private final ApiUseTimeRepository apiUseTimeRepository;

	@GetMapping("/api/use/time")
	@Secured(UserRoleEnum.Authority.ADMIN)
	public List<ApiUseTime> getAllApiUseTime() {
		return apiUseTimeRepository.findAll();
	}
}
