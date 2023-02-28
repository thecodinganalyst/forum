package com.hevlar.forum.controller.mapper;

import com.hevlar.forum.controller.dto.UserRoleDto;
import com.hevlar.forum.model.ForumRole;
import com.hevlar.forum.model.ForumUser;

public class ForumUserToUserRoleMapper {
    private ForumUserToUserRoleMapper(){
        throw new IllegalStateException("Mapper class");
    }
    public static UserRoleDto map(ForumUser forumUser){
        return new UserRoleDto(
                forumUser.getUserId(),
                forumUser.getGivenName(),
                forumUser.getFamilyName(),
                forumUser.getEmail(),
                forumUser.getRoles()
                        .stream()
                        .map(ForumRole::getName)
                        .toList()
        );
    }
}
