package com.rara.my_selectshop.service.impl;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import com.rara.my_selectshop.dto.ProductResponseDto;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.entity.User;
import com.rara.my_selectshop.entity.UserRoleEnum;
import com.rara.my_selectshop.jwt.JwtUtil;
import com.rara.my_selectshop.naver.dto.ItemDto;
import com.rara.my_selectshop.repository.ProductRepository;
import com.rara.my_selectshop.repository.UserRepository;
import com.rara.my_selectshop.service.ProductService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final UserRepository userRepository;

	private final JwtUtil jwtUtil;

	@Transactional
	public ProductResponseDto createProduct(ProductRequestDto requestDto, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		Claims claims;

		// 토큰이 있는 경우에만 관심상품 추가 가능
		if (token != null) {
			if (jwtUtil.validateToken(token)) {
				// 토큰에서 사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("Token Error");
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);

			// 요청받은 DTO 로 DB에 저장할 객체 만들기
			Product product = productRepository.saveAndFlush(new Product(requestDto, user.getId()));
			return new ProductResponseDto(product);
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<ProductResponseDto> getProducts(HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		Claims claims;

		// 토큰이 있는 경우에만 관심상품 조회 가능
		if (token != null) {
			// Token 검증
			if (jwtUtil.validateToken(token)) {
				// 토큰에서 사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("Token Error");
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);

			// 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
			UserRoleEnum userRoleEnum = user.getRole();
			System.out.println("role = " + userRoleEnum);

			List<ProductResponseDto> list = new ArrayList<>();
			List<Product> productList;

			if (userRoleEnum == UserRoleEnum.USER) {
				productList = productRepository.findAllByUserId(user.getId());
			} else {
				productList = productRepository.findAll();
			}

			for (Product product : productList) {
				list.add(new ProductResponseDto(product));
			}
			return list;
		} else {
			return null;
		}
	}

	@Transactional
	public Long updateProduct(Long id, ProductMypriceRequestDto requestDto, HttpServletRequest request) {
		String token = jwtUtil.resolveToken(request);
		Claims claims;

		// 토큰이 있는 경우에만 관심상품 최저가 업데이트 가능
		if (token != null) {
			// Token 검증
			if (jwtUtil.validateToken(token)) {
				// 토큰에서 사용자 정보 가져오기
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("Token Error");
			}

			// 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);

			Product product = productRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
				() -> new NullPointerException("해당 상품은 존재하지 않습니다.")
			);

			product.update(requestDto);

			return product.getId();

		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public void updateBySearch(Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id).orElseThrow(
			() -> new NullPointerException("해당 상품은 존재하지 않습니다.")
		);
		product.updateByItemDto(itemDto);
	}
}
