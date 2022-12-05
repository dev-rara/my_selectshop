package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllByUserId(Long userId);

	Optional<Product> findByIdAndUserId(Long id, Long userId);
}