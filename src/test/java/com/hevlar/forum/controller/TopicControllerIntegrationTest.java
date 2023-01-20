package com.hevlar.forum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hevlar.forum.model.Topic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class TopicControllerIntegrationTest{

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    final String topicsEndpoint = "/api/v1/topics";

    @Test
    public void whenPostTopic_thenReturnStatusCreatedAndTopic() throws Exception {
        Topic topic = new Topic("Topic");
        mvc.perform(post(topicsEndpoint).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(topic)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Topic"))
                .andExpect(jsonPath("$.created").isNotEmpty())
                .andExpect(jsonPath("$.updated").isNotEmpty());
    }

}
