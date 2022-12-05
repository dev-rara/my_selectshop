package com.rara.my_selectshop.service.impl;

import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.entity.User;
import com.rara.my_selectshop.jwt.JwtUtil;
import com.rara.my_selectshop.repository.FolderRepository;
import com.rara.my_selectshop.repository.ProductRepository;
import com.rara.my_selectshop.repository.UserRepository;
import com.rara.my_selectshop.service.FolderService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

	private final FolderRepository folderRepository;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final ProductRepository productRepository;


	// 로그인한 회원에 폴더들 등록
	@Transactional
	@Override
	public List<Folder> addFolders(List<String> folderNames, HttpServletRequest request) {

		// Request에서 Token 가져오기
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

			//입력받은 폴더명을 기준으로, 이미 생성한 폴더들을 조회
			List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);

			List<Folder> folderList = new ArrayList<>();

			for (String folderName : folderNames) {
				// 이미 생성한 폴더가 아닌 경우만 폴더 생성
				if (!isExistFolderName(folderName, existFolderList)) {
					Folder folder = new Folder(folderName, user);
					folderList.add(folder);
				}
			}

			return folderRepository.saveAll(folderList);
		} else {
			return null;
		}
	}

	// 로그인한 회원이 등록된 모든 폴더 조회
	@Override
	public List<Folder> getFolders(HttpServletRequest request) {

		// 사용자의 정보를 가져온다
		// Request에서 Token 가져오기
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

			return folderRepository.findAllByUser(user);

		} else {
			return null;
		}
	}

	@Override
	@Transactional
	public Page<Product> getProductsInFolder(Long folderId, int page, int size, String sortBy,
		boolean isAsc, HttpServletRequest request) {
		//페이징 처리
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		//Request 에서 Token 가져오기
		String token = jwtUtil.resolveToken(request);
		Claims claims;

		//토근이 있는 경우에만 관심상품 조회 가능
		if(token != null) {
			if(jwtUtil.validateToken(token)) {
				claims = jwtUtil.getUserInfoFromToken(token);
			} else {
				throw new IllegalArgumentException("Token Error");
			}

			//토큰에서 가져온 사용자 정보를 사용하여 DB 조최
			User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
				() -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
			);

			return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
		} else {
			return null;
		}
	}

	private boolean isExistFolderName(String folderName, List<Folder> existFolderList) {
		// 기존 폴더 리스트에서 folder name 이 있는지?
		for (Folder existFolder : existFolderList) {
			if (existFolder.getName().equals(folderName)) {
				return true;
			}
		}
		return false;
	}

}

