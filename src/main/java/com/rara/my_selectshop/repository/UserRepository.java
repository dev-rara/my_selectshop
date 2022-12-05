package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
