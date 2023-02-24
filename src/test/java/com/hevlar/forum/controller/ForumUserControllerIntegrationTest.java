package com.hevlar.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.forum.controller.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.hevlar.forum.ApplicationStartupRunner.USER_ROLE_NAME;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ForumUserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void givenUserRegistrationDtoIsValid_whenRegisterUser_shouldSucceed() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "givenName",
                "familyName",
                "email@forum.com",
                "Password123",
                "Password123"
        );
        String userRegistrationDtoJson = objectMapper.writeValueAsString(userRegistrationDto);

        // when
        mockMvc.perform(post("/api/v1/users/registerUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userRegistrationDtoJson).with(csrf()))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.userId").value("user1"),
                        jsonPath("$.givenName").value("givenName"),
                        jsonPath("$.familyName").value("familyName"),
                        jsonPath("$.email").value("email@forum.com"),
                        jsonPath("$.roles").isArray(),
                        jsonPath("$.roles[0]").value(USER_ROLE_NAME)
                );

    }
}