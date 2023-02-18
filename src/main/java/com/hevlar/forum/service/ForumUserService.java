package com.hevlar.forum.service;

import com.hevlar.forum.controller.dto.UserDto;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.persistence.ForumUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForumUserService {

    ForumUserRepository repository;
    PasswordEncoder passwordEncoder;

    public ForumUserService(ForumUserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public ForumUser registerUser(UserDto dto) throws Exception {
        if(repository.existsByEmail(dto.email())){
            throw new Exception("Email already exists");
        }
        if(repository.existsById(dto.userId())){
            throw new Exception("User Id already exists");
        }
        ForumUser user = new ForumUser(dto.userId(), dto.givenName(), dto.familyName(), dto.email(), passwordEncoder.encode(dto.password()), true, false);
        return repository.save(user);
    }

}
