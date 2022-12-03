package com.rara.my_selectshop.naver.service;

import com.rara.my_selectshop.naver.dto.ItemDto;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NaverApiService {

	public List<ItemDto> searchItems(String query) {
		RestTemplate rest = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Naver-Client-Id", "emLqBdcZUX18ZF7G_r0l");
		headers.add("X-Naver-Client-Secret", "MTvr5LMyaC");
		String body = "";

		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
			"https://openapi.naver.com/v1/search/shop.json?display=15&query=" + query,
			HttpMethod.GET, requestEntity, String.class);

		HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();
		int status = httpStatus.value();
		log.info("NAVER API Status Code : " + status);

		String response = responseEntity.getBody();

		return fromJSONtoItems(response);
	}

	public List<ItemDto> fromJSONtoItems(String response) {

		JSONObject rjson = new JSONObject(response);
		JSONArray items = rjson.getJSONArray("items");
		List<ItemDto> itemDtoList = new ArrayList<>();

		for (int i = 0; i < items.length(); i++) {
			JSONObject itemJson = items.getJSONObject(i);
			ItemDto itemDto = new ItemDto(itemJson);
			itemDtoList.add(itemDto);
		}

		return itemDtoList;
	}

}

