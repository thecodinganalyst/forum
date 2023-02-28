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
    ApplicationStartupConfig applicationStartupConfig;

    public ApplicationStartupRunner(ForumUserService userService, ForumRoleService roleService, PasswordEncoder passwordEncoder, ApplicationStartupConfig applicationStartupConfig){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.applicationStartupConfig = applicationStartupConfig;
    }

    @Override
    public void run(ApplicationArguments args) {
        ForumRole adminRole = new ForumRole(applicationStartupConfig.getAdminRoleId(), applicationStartupConfig.getAdminRoleName());
        roleService.create(adminRole);

        ForumRole userRole = new ForumRole(applicationStartupConfig.getUserRoleId(), applicationStartupConfig.getUserRoleName());
        roleService.create(userRole);

        ForumUser adminUser = new ForumUser(
                applicationStartupConfig.getAdminUserId(),
                "Administrator",
                "Forum",
                applicationStartupConfig.getAdminUserEmail(),
                passwordEncoder.encode(applicationStartupConfig.getAdminUserPassword())
        );
        userService.createAdmin(adminUser);
    }

}
