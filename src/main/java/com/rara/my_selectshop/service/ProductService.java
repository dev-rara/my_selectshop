package com.rara.my_selectshop.service;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import com.rara.my_selectshop.naver.dto.ItemDto;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {
	ProductResponseDto createProduct(ProductRequestDto requestDto, HttpServletRequest request);

	List<ProductResponseDto> getProducts(HttpServletRequest request);

	Long updateProduct(Long id, ProductMypriceRequestDto requestDto, HttpServletRequest request);

	void updateBySearch(Long id, ItemDto itemDto);
}
