package com.rara.my_selectshop.entity;

import com.rara.my_selectshop.dto.ProductMypriceRequestDto;
import com.rara.my_selectshop.dto.ProductRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity // DB 테이블 역할을 합니다.
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID가 자동으로 생성 및 증가합니다.
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String link;

	@Column(nullable = false)
	private int lprice;

	@Column(nullable = false)
	private int myprice;

	public Product(ProductRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.image = requestDto.getImage();
		this.link = requestDto.getLink();
		this.lprice = requestDto.getLprice();
		this.myprice = 0;
	}

	public void update(ProductMypriceRequestDto requestDto) {
		this.myprice = requestDto.getMyprice();
	}
}
