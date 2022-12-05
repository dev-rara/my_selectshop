package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}