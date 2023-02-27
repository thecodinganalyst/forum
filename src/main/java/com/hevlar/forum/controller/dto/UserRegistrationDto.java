package com.hevlar.forum.controller.dto;

import com.hevlar.forum.validation.PasswordMatching;
import com.hevlar.forum.validation.ValidEmail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@PasswordMatching(message = "Passwords do not match")
public record UserRegistrationDto(

        @NotNull(message = "User id cannot be null")
        @NotEmpty(message = "User id cannot be empty")
        String userId,

        @NotNull(message = "Given name cannot be null")
        @NotEmpty(message = "Given name cannot be empty")
        String givenName,

        @NotNull(message = "Family name cannot be null")
        @NotEmpty(message = "Family name cannot be empty")
        String familyName,

        @ValidEmail(message = "Email is not valid")
        @NotNull(message = "Email cannot be null")
        @NotEmpty(message = "Email cannot be empty")
        String email,

        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,
        @NotNull(message = "Matching password cannot be null")
        @NotEmpty(message = "Matching password cannot be empty")
        String matchingPassword
) {
}
