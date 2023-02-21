package com.hevlar.forum.service;

import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.persistence.ForumRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumRoleService {

    ForumRoleRepository repository;

    public ForumRoleService(ForumRoleRepository repository){
        this.repository = repository;
    }

    public ForumRole create(ForumRole forumRole){
        return this.repository.save(forumRole);
    }

    public ForumRole update(ForumRole forumRole){
        ForumRole retrievedRole = this.repository.findById(forumRole.getRoleId())
                .orElseThrow();
        retrievedRole.setName(forumRole.getName());
        retrievedRole.setValid(forumRole.getValid());
        return this.repository.save(retrievedRole);
    }

    public List<ForumRole> list(){
        return this.repository.findAll();
    }

    public ForumRole get(String roleId){
        return this.repository.findById(roleId).orElseThrow();
    }

    public ForumRole getUserRole(){
        return this.repository.findById("USER").orElseThrow();
    }

    public ForumRole getAdminRole(){
        return this.repository.findById("ADMIN").orElseThrow();
    }
}
