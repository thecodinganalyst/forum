package com.hevlar.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.forum.controller.dto.ErrorDto;
import com.hevlar.forum.controller.dto.UserRegistrationDto;
import com.hevlar.forum.ApplicationStartupConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ForumUserControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationStartupConfig applicationStartupConfig;

    @Test
    @Order(1)
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
                        jsonPath("$.roles[0]").value(applicationStartupConfig.getUserRoleName())
                );

    }

    @Test
    @Order(2)
    void givenUserAlreadyExists_whenRegisterUser_shouldFail() throws Exception {
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
                .andExpect(status().isConflict())
                .andExpect(status().reason("Email already exists"));

    }

    @Test
    @Order(3)
    void givenUserIdAlreadyExists_whenRegisterUser_shouldFail() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "givenName",
                "familyName",
                "email@forum2.com",
                "Password123",
                "Password123"
        );
        String userRegistrationDtoJson = objectMapper.writeValueAsString(userRegistrationDto);

        // when
        mockMvc.perform(post("/api/v1/users/registerUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationDtoJson).with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(status().reason("User Id already exists"));

    }

    @Test
    @Order(4)
    void whenRegisterUserWithEmptyFields_shouldFail() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "",
                "",
                "",
                "",
                "",
                ""
        );
        String userRegistrationDtoJson = objectMapper.writeValueAsString(userRegistrationDto);

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/users/registerUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationDtoJson).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(8))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("userRegistrationDto", "userId", "", "User id cannot be empty"),
                new ErrorDto("userRegistrationDto", "givenName", "", "Given name cannot be empty"),
                new ErrorDto("userRegistrationDto", "familyName", "", "Family name cannot be empty"),
                new ErrorDto("userRegistrationDto", "email", "", "Email is not valid"),
                new ErrorDto("userRegistrationDto", "email", "", "Email cannot be empty"),
                new ErrorDto("userRegistrationDto", "password", "", "Password cannot be empty"),
                new ErrorDto("userRegistrationDto", "matchingPassword", "", "Matching password cannot be empty"),
                new ErrorDto("userRegistrationDto", "password", "", "Password must be at least 8 characters long")
        );

        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }

    @Test
    @Order(5)
    void whenRegisterUserWithNonMatchingPasswords_shouldFail() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "givenName",
                "familyName",
                "email@forum2.com",
                "Password123",
                "Password1234"
        );
        String userRegistrationDtoJson = objectMapper.writeValueAsString(userRegistrationDto);

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/users/registerUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationDtoJson).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("userRegistrationDto", null, null, "Passwords do not match")
        );

        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }

    @Test
    @Order(5)
    void whenRegisterUserWithInvalidEmail_shouldFail() throws Exception {
        // given
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(
                "user1",
                "givenName",
                "familyName",
                "email@forum",
                "Password123",
                "Password123"
        );
        String userRegistrationDtoJson = objectMapper.writeValueAsString(userRegistrationDto);

        // when
        MvcResult result = mockMvc.perform(post("/api/v1/users/registerUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRegistrationDtoJson).with(csrf()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("userRegistrationDto", "email", "email@forum", "Email is not valid")
        );

        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }

}