package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByIdAndUserId(Long id, Long userId);

	Page<Product> findAllByUserId(Long userId, Pageable pageable);

	Page<Product> findAll(Pageable pageable);

	Page<Product> findAllByUserIdAndFolderList_Id(Long id, Long folderId, Pageable pageable);
}