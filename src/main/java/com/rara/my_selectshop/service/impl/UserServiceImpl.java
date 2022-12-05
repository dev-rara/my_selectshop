package com.rara.my_selectshop.service.impl;

import com.rara.my_selectshop.dto.LoginRequestDto;
import com.rara.my_selectshop.dto.SignupRequestDto;
import com.rara.my_selectshop.entity.User;
import com.rara.my_selectshop.entity.UserRoleEnum;
import com.rara.my_selectshop.repository.UserRepository;
import com.rara.my_selectshop.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	@Override
	@Transactional
	public void signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = signupRequestDto.getPassword();
		String email = signupRequestDto.getEmail();

		// 회원 중복 확인
		Optional<User> found = userRepository.findByUsername(username);
		if (found.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
		}

		// 사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.USER;
		if (signupRequestDto.isAdmin()) {
			if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
				throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
			}
			role = UserRoleEnum.ADMIN;
		}

		User user = new User(username, password, email, role);
		userRepository.save(user);
	}

}
