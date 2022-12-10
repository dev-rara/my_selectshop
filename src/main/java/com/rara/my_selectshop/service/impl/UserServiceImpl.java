package com.rara.my_selectshop.service.impl;

import com.rara.my_selectshop.dto.LoginRequestDto;
import com.rara.my_selectshop.dto.SignupRequestDto;
import com.rara.my_selectshop.entity.User;
import com.rara.my_selectshop.entity.UserRoleEnum;
import com.rara.my_selectshop.jwt.JwtUtil;
import com.rara.my_selectshop.repository.UserRepository;
import com.rara.my_selectshop.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	@Override
	@Transactional
	public void signup(SignupRequestDto signupRequestDto) {
		String username = signupRequestDto.getUsername();
		String password = passwordEncoder.encode(signupRequestDto.getPassword());
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

	@Override
	@Transactional(readOnly = true)
	public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
		String username = loginRequestDto.getUsername();
		String password = loginRequestDto.getPassword();

		//사용자 확인
		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException("등록된 사용자가 없습니다.")
		);

		//비밀번호 확인
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}

		response.addHeader(
			JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
	}

}
