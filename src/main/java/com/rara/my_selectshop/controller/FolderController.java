package com.rara.my_selectshop.controller;

import com.rara.my_selectshop.dto.FolderRequestDto;
import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.service.FolderService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

	private final FolderService folderService;

	@PostMapping("/folders")
	public List<Folder> addFolders(
		@RequestBody FolderRequestDto folderRequestDto,
		HttpServletRequest request
	) {

		List<String> folderNames = folderRequestDto.getFolderNames();

		return folderService.addFolders(folderNames, request);
	}
}
