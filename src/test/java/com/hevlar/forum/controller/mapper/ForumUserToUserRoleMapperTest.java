package com.hevlar.forum.controller.mapper;

import com.hevlar.forum.controller.dto.UserRoleDto;
import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.model.ForumUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
class ForumUserToUserRoleMapperTest {

    @Test
    void whenMapForumUserToUserDto_thenMapCorrectly(){
        ForumUser forumUser = new ForumUser("userId", "givenName", "familyName", "email@forum.com", "password", true, false);
        ForumRole userRole = new ForumRole("user", "User", true);
        ForumRole adminRole = new ForumRole("admin", "Administrator", true);
        forumUser.setRoles(List.of(userRole, adminRole));

        UserRoleDto userRoleDto = ForumUserToUserRoleMapper.map(forumUser);

        assertThat(userRoleDto.userId(), is("userId"));
        assertThat(userRoleDto.givenName(), is("givenName"));
        assertThat(userRoleDto.familyName(), is("familyName"));
        assertThat(userRoleDto.email(), is("email@forum.com"));
        assertThat(userRoleDto.roles().size(), is(2));
        assertThat(userRoleDto.roles(), contains("User", "Administrator"));
    }

}