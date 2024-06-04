package com.example.mediready.domain.folder;

import com.example.mediready.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByUser(User user);

    List<Folder> findFoldersByUser(User user);

    boolean existsByIdAndUser(Long folderId, User user);

    Folder findByUserAndPriority(User user, int priority);
}
