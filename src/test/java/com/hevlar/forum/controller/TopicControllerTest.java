package com.hevlar.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.forum.model.Topic;
import com.hevlar.forum.security.ForumSecurityConfiguration;
import com.hevlar.forum.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ForumSecurityConfiguration.class, TopicController.class})
@WebMvcTest
@WithMockUser(username = "USER")
class TopicControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TopicService topicService;

    @Autowired
    ObjectMapper objectMapper;

    final String api = "/api/v1/topics";

    @Test
    void givenTopicsExists_whenListTopic_thenReturnTopicList() throws Exception {
        List<Topic> topicList = List.of(
                new Topic("first topic"),
                new Topic("second topic")
        );
        given(topicService.list()).willReturn(topicList);

        mockMvc.perform(get(api).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void whenCreateTopic_thenReturn301CreatedAndTopic() throws Exception {
        Topic topic = Topic.builder()
                .topicId(1L)
                .title("topic")
                .build();
        given(topicService.create(any())).willReturn(topic);

        RequestBuilder request = post(api)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(topic))
                .with(csrf());
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        jsonPath("$.topicId").value("1"),
                        jsonPath("$.title").value("topic")
                );
    }

    @Test
    void givenTopicDoesNotExists_whenGetTopic_thenReturn404NotFound() throws Exception {
        given(topicService.get(1L)).willThrow(NoSuchElementException.class);
        mockMvc.perform(get(api + "/1").with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenTopicDoesNotExists_whenPutTopic_thenReturn404NotFound() throws Exception {
        given(topicService.update(any())).willThrow(NoSuchElementException.class);
        Topic topic = new Topic("topic");
        mockMvc.perform(
                put(api).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(topic))
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenTopicDoesNotExists_whenDeleteTopic_thenReturn404NotFound() throws Exception {
        doThrow(NoSuchElementException.class).when(topicService).delete(1L);
        mockMvc.perform(delete(api + "/1").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
