package com.rara.my_selectshop.controller;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	// 관심 상품 등록하기
	@PostMapping("/products")
	public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, HttpServletRequest request) {
		return productService.createProduct(requestDto, request);
	}

	// 관심 상품 조회하기
	@GetMapping("/products")
	public Page<Product> getProducts(
		@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam("sortBy") String sortBy,
		@RequestParam("isAsc") boolean isAsc,
		HttpServletRequest request
	) {
		// 응답 보내기
		return productService.getProducts(request, page-1, size, sortBy, isAsc);
	}

	// 관심 상품 최저가 등록하기
	@PutMapping("/products/{id}")
	public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto, HttpServletRequest request) {
		return productService.updateProduct(id, requestDto, request);
	}

}

