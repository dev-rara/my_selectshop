package com.rara.my_selectshop.service;

import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.entity.Product;
import com.rara.my_selectshop.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface FolderService {

	List<Folder> addFolders(List<String> folderNames, String name);
	List<Folder> getFolders(User user);
	Page<Product> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user);
}
