package com.rara.my_selectshop.service.impl;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.repository.ProductRepository;
import com.rara.my_selectshop.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		Product product = productRepository.saveAndFlush(new Product(requestDto));
		return new ProductResponseDto(product);
	}

	@Transactional(readOnly = true)
	public List<ProductResponseDto> getProducts() {
		List<ProductResponseDto> list = new ArrayList<>();
		List<Product> productList = productRepository.findAll();
		for (Product product : productList) {
			list.add(new ProductResponseDto(product));
		}
		return list;
	}

	@Transactional
	public Long updateProduct(Long id, ProductMypriceRequestDto requestDto) {
		Product product = productRepository.findById(id).orElseThrow(
			() -> new NullPointerException("해당 상품은 존재하지 않습니다.")
		);
		product.update(requestDto);
		return product.getId();
	}

}
