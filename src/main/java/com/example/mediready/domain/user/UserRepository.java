package com.example.mediready.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findUserByEmailAndDeletedFalse(String email);

    Optional<User> findUserByIdAndDeletedFalse(Long userId);
}
