package com.rara.my_selectshop.naver.controller;

import com.rara.my_selectshop.naver.dto.ItemDto;
import com.rara.my_selectshop.naver.service.NaverApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NaverApiController {

	private final NaverApiService naverApiService;

	@GetMapping("/search")
	public List<ItemDto> searchItems(@RequestParam String query)  {
		return naverApiService.searchItems(query);
	}
}
