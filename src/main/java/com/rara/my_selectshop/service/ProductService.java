package com.rara.my_selectshop.service;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.entity.User;
import com.rara.my_selectshop.naver.dto.ItemDto;
import org.springframework.data.domain.Page;

public interface ProductService {
	ProductResponseDto createProduct(ProductRequestDto requestDto, User user);

	Page<Product> getProducts(User user,
		int page, int size, String sortBy, boolean isAsc);

	Long updateProduct(Long id, ProductMypriceRequestDto requestDto, User user);

	void updateBySearch(Long id, ItemDto itemDto);

	Product addFolder(Long productId, Long folderId, User user);
}
