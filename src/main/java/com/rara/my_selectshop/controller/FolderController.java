package com.rara.my_selectshop.controller;

import com.rara.my_selectshop.dto.FolderRequestDto;
import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.security.UserDetailsImpl;
import com.rara.my_selectshop.service.FolderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

	private final FolderService folderService;

	@PostMapping("/folders")
	public List<Folder> addFolders(
		@RequestBody FolderRequestDto folderRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {

		List<String> folderNames = folderRequestDto.getFolderNames();

		System.out.println("======================================================");
		System.out.println("user.getUsername() = " + userDetails.getUsername());
		System.out.println("user.getUser() = " + userDetails.getUser());
		System.out.println("user.getUser().getPassword() = " + userDetails.getUser().getPassword());
		System.out.println("user.getUser().getId() = " + userDetails.getUser().getId());
		System.out.println("======================================================");

		return folderService.addFolders(folderNames, userDetails.getUsername());
	}

	// 회원이 등록한 모든 폴더 조회
	@GetMapping("/folders")
	public List<Folder> getFolders(
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return folderService.getFolders(userDetails.getUser());
	}

	// 회원이 등록한 폴더 내 모든 상품 조회
	@GetMapping("/folders/{folderId}/products")
	public Page<Product> getProductsInFolder(
		@PathVariable Long folderId,
		@RequestParam int page,
		@RequestParam int size,
		@RequestParam String sortBy,
		@RequestParam boolean isAsc,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return folderService.getProductsInFolder(
			folderId,
			page - 1,
			size,
			sortBy,
			isAsc,
			userDetails.getUser()
		);
	}
}