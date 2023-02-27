package com.hevlar.forum;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.config")
@Getter
@Setter
public class ApplicationStartupConfig {
    String adminRoleId;
    String adminRoleName;
    String userRoleId;
    String userRoleName;
    String adminUserId;
    String adminUserEmail;
    String adminUserPassword;
}
