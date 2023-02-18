package com.hevlar.forum.persistence;

import com.hevlar.forum.model.ForumRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRoleRepository extends JpaRepository<ForumRole, String> {
}
