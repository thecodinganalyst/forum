package com.hevlar.forum.controller.dto;

import com.hevlar.forum.validation.PasswordMatching;
import com.hevlar.forum.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@PasswordMatching
public record UserDto(

        @NotNull
        @NotEmpty
        String userId,

        @NotNull
        @NotEmpty
        String givenName,

        @NotNull
        @NotEmpty
        String familyName,

        @ValidEmail
        @NotNull
        @NotEmpty
        String email,

        @NotNull
        @NotEmpty
        String password,
        String matchingPassword
) {
}
