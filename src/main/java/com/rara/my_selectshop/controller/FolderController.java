package com.rara.my_selectshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
