package com.hevlar.forum.security;

import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.persistence.ForumUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ForumUserDetailsService implements UserDetailsService {

    ForumUserRepository repository;
    public ForumUserDetailsService(ForumUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        ForumUser user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User id " + userId + " not found"));

        return new User(
                user.getUserId(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(it -> new SimpleGrantedAuthority(it.getName())).toList());
    }
}
