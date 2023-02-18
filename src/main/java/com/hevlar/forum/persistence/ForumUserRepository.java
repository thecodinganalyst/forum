package com.hevlar.forum.persistence;

import com.hevlar.forum.model.ForumUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumUserRepository extends JpaRepository<ForumUser, String> {
    boolean existsByEmail(String email);
}
