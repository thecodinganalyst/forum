package com.hevlar.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.forum.model.Post;
import com.hevlar.forum.model.Topic;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
class PostControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    final String postEndpoint = "/api/v1/posts";
    Topic topic;

    @BeforeAll
    void setup() throws Exception {
        topic = Topic.builder().topicId(1L).title("Topic").build();
        MvcResult result = mvc.perform(post("/api/v1/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topic))
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.topicId").value(1))
                .andReturn();
        topic = objectMapper.readValue(result.getResponse().getContentAsString(), Topic.class);
    }

    @Test
    @Order(1)
    void whenCreatePost_thenReturnStatusCreatedAndPost() throws Exception {
        Post post = Post.builder().text("New Post").topic(topic).build();
        String postJson = objectMapper.writeValueAsString(post);
        mvc.perform(post(postEndpoint).contentType(MediaType.APPLICATION_JSON).content(postJson).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.text").value("New Post"),
                        jsonPath("$.created").isNotEmpty(),
                        jsonPath("$.updated").isNotEmpty()
                );
    }

    @Test
    @Order(2)
    void whenListPost_thenReturnPostList() throws Exception {
        var result = mvc.perform(get("/api/v1/topics/1/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].text").value("New Post"));
        assertThat(result, is(notNullValue()));
    }

    @Test
    @Order(3)
    void whenPutPost_thenReturnStatusOkAndUpdatedPost() throws Exception {
        Post post = Post.builder().postId(1L).text("Updated Post").topic(topic).build();
        mvc.perform(put("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("Updated Post"));
    }

    @Test
    @Order(4)
    void whenGetPost_thenReturnStatusOkAndPost() throws Exception {
        mvc.perform(get("/api/v1/posts/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").value("Updated Post"));
    }


}
