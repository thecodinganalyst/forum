package com.hevlar.forum.controller.dto;

import java.util.List;

public record UserRoleDto(
        String userId,
        String givenName,
        String familyName,
        String email,
        List<String> roles
) {
}
