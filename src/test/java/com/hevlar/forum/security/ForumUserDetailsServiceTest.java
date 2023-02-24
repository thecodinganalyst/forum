package com.hevlar.forum.security;

import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.persistence.ForumUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ForumUserDetailsServiceTest {

    @Mock
    ForumUserRepository forumUserRepository;

    @InjectMocks
    ForumUserDetailsService forumUserDetailsService;

    @Test
    void givenUserIsValid_whenLoadUserByUserName_thenReturnUser() {
        // given
        ForumUser forumUser = new ForumUser("userId", "givenName", "familyName", "email@forum.com", "password", true, false);
        ForumRole userRole = new ForumRole("user", "User", true);
        forumUser.setRoles(List.of(userRole));
        given(forumUserRepository.findById("userId")).willReturn(Optional.of(forumUser));

        // when
        UserDetails userDetails = forumUserDetailsService.loadUserByUsername("userId");

        // then
        assertThat(userDetails.getUsername(), is("userId"));
        assertThat(userDetails.getPassword(), is("password"));
        assertThat(userDetails.getAuthorities().size(), is(1));

        List<String> authorities = userDetails.getAuthorities()
                                                    .stream()
                                                    .map(GrantedAuthority::getAuthority)
                                                    .toList();

        assertThat(authorities, contains("User"));
    }

    @Test
    void givenUserIsInvalid_whenLoadUserByUserName_thenReturnUser() {
        // given
        given(forumUserRepository.findById("userId")).willReturn(Optional.empty());

        // when
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> forumUserDetailsService.loadUserByUsername("userId"));

        // then
        assertThat(exception.getMessage(), is("User id userId not found"));
    }
}