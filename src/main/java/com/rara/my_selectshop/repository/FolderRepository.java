package com.rara.my_selectshop.repository;

import com.rara.my_selectshop.entity.Folder;
import com.rara.my_selectshop.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
	List<Folder> findAllByUser(User user);
}
