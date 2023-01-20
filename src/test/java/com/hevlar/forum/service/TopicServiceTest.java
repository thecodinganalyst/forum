package com.hevlar.forum.service;

import com.hevlar.forum.model.Topic;
import com.hevlar.forum.persistence.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {
    @Mock
    TopicRepository topicRepository;

    @InjectMocks
    TopicService topicService;

    @Test
    public void givenTopicAlreadyExists_whenTopicIsUpdated_thenTopicCreatedIsFromRepository(){
        //given
        LocalDateTime createdDateTime = LocalDateTime.now().minusHours(1);
        Topic topic = Topic.builder()
                .topicId(1L)
                .title("First Topic")
                .created(createdDateTime)
                .updated(createdDateTime)
                .build();
        given(topicRepository.findById(1L)).willReturn(Optional.of(topic));

        //when
        Topic updatedTopic = Topic.builder().topicId(1L).title("Updated Topic").build();
        topicService.update(updatedTopic);

        //then
        assertThat(updatedTopic.getCreated(), is(createdDateTime));
    }

    @Test()
    public void givenTopicDoesNotExist_whenDelete_thenThrowException(){
        //given
        given(topicRepository.existsById(1L)).willReturn(false);

        //when
        Throwable throwable = assertThrows(NoSuchElementException.class, () -> topicService.delete(1L));

        //then
        assertThat(throwable, is(notNullValue()));
    }
}
