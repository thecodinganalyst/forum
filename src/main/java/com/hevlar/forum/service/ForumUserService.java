package com.hevlar.forum.service;

import com.hevlar.forum.controller.dto.UserRegistrationDto;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.persistence.ForumUserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ForumUserService {

    ForumUserRepository repository;
    PasswordEncoder passwordEncoder;
    ForumRoleService roleService;

    public ForumUserService(ForumUserRepository repository, PasswordEncoder passwordEncoder, ForumRoleService roleService){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public ForumUser registerUser(UserRegistrationDto dto) {
        if(repository.existsByEmail(dto.email())){
            throw new DuplicateKeyException("Email already exists");
        }
        if(repository.existsById(dto.userId())){
            throw new DuplicateKeyException("User Id already exists");
        }
        ForumUser user = new ForumUser(dto.userId(), dto.givenName(), dto.familyName(), dto.email(), passwordEncoder.encode(dto.password()), true, false);
        user.setRoles(List.of(roleService.getUserRole()));
        return repository.save(user);
    }

    public ForumUser createAdmin(ForumUser forumUser){
        forumUser.setRoles(List.of(roleService.getAdminRole()));
        return repository.save(forumUser);
    }

    public ForumUser getUserByUserId(String userId){
        return repository.findById(userId).orElseThrow();
    }

}
