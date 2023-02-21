package com.hevlar.forum;

import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.service.ForumRoleService;
import com.hevlar.forum.service.ForumUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements ApplicationRunner {

    ForumUserService userService;
    ForumRoleService roleService;

    PasswordEncoder passwordEncoder;

    public ApplicationStartupRunner(ForumUserService userService, ForumRoleService roleService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public static String USER_ROLE_ID = "USER";
    public static String ADMIN_ROLE_ID = "ADMIN";
    public static String ADMIN_USER_ID = "ADMIN";

    @Override
    public void run(ApplicationArguments args) {
        roleService.create(new ForumRole(ADMIN_ROLE_ID, "Administrator", true));
        roleService.create(new ForumRole(USER_ROLE_ID, "User", true));
        userService.createAdmin(new ForumUser(ADMIN_USER_ID, "Administrator", "Forum", "admin@forum.com", passwordEncoder.encode("password"), true, false));
    }
}
