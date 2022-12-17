package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.ApiUseTime;
import com.rara.my_selectshop.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
	Optional<ApiUseTime> findByUser(User user);
}
