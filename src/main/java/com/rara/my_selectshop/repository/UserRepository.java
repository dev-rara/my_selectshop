package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);
}
