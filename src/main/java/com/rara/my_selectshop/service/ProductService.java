package com.rara.my_selectshop.service;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
	ProductResponseDto createProduct(ProductRequestDto requestDto);

	List<ProductResponseDto> getProducts();

	Long updateProduct(Long id, ProductMypriceRequestDto requestDto);
}
