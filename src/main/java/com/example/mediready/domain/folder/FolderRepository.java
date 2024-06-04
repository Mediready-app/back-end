package com.example.mediready.domain.folder;

import com.example.mediready.domain.user.User;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f FROM Folder f WHERE f.user.id = :userId ORDER BY f.priority DESC LIMIT 1")
    Optional<Folder> findTopByUserIdOrderByPriorityDesc(@Param("userId") Long userId);

    List<Folder> findFoldersByUser(User user);


    Folder findByUserAndPriority(User user, int priority);

    Optional<Folder> findByIdAndUser(Long id, User user);
}
