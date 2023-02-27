package com.hevlar.forum;

import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.service.ForumRoleService;
import com.hevlar.forum.service.ForumUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest
class ForumApplicationTest {

    @Autowired
    ApplicationStartupConfig applicationStartupConfig;

    @Autowired
    ForumUserService forumUserService;

    @Autowired
    ForumRoleService forumRoleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void applicationStartupConfig(){
        assertThat(applicationStartupConfig.adminRoleId, is("ADMIN_ROLE"));
        assertThat(applicationStartupConfig.adminRoleName, is("Administrator"));
        assertThat(applicationStartupConfig.userRoleId, is("USER_ROLE"));
        assertThat(applicationStartupConfig.userRoleName, is("User"));
        assertThat(applicationStartupConfig.adminUserId, is("Admin"));
        assertThat(applicationStartupConfig.adminUserEmail, is("admin@forum.com"));
        assertThat(applicationStartupConfig.adminUserPassword, is("P@ssw0rd123"));
    }

    @Test
    void applicationStartupRunner_createDefaultRolesAndAdminUser(){
        ForumRole adminRole = forumRoleService.getAdminRole();
        assertThat(adminRole.getRoleId(), is("ADMIN_ROLE"));
        assertThat(adminRole.getName(), is("Administrator"));

        ForumRole userRole = forumRoleService.getUserRole();
        assertThat(userRole.getRoleId(), is("USER_ROLE"));
        assertThat(userRole.getName(), is("User"));

        ForumUser adminUser = forumUserService.getUserByUserId("Admin");
        assertThat(adminUser, is(notNullValue()));
        assertThat(adminUser.getUserId(), is("Admin"));
        assertThat(adminUser.getEmail(), is("admin@forum.com"));

        List<ForumRole> adminUserRoleList = adminUser.getRoles().stream().toList();
        assertThat(adminUserRoleList.size(), is(1));
        assertThat(adminUserRoleList.get(0).getRoleId(), is("ADMIN_ROLE"));
    }
}