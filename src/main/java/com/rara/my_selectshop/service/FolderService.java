package com.rara.my_selectshop.service;

import com.rara.my_selectshop.entity.Folder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface FolderService {

	List<Folder> addFolders(List<String> folderNames, HttpServletRequest request);
	List<Folder> getFolders(HttpServletRequest request);
}
