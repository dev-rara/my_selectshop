package com.rara.my_selectshop.service;

import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.entity.Product;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Page;

public interface FolderService {

	List<Folder> addFolders(List<String> folderNames, HttpServletRequest request);
	List<Folder> getFolders(HttpServletRequest request);
	Page<Product> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, HttpServletRequest request);
}
