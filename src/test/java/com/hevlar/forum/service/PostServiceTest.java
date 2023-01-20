package com.hevlar.forum.service;

import com.hevlar.forum.model.Post;
import com.hevlar.forum.model.Topic;
import com.hevlar.forum.persistence.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    public void givenPostAlreadyExists_whenPostIsUpdated_thenPostCreatedIsFromRepository(){
        //given
        Topic topic = new Topic("Topic");
        LocalDateTime createdDateTime = LocalDateTime.now().minusHours(1);
        Post post = Post.builder()
                .postId(1L)
                .text("post")
                .created(createdDateTime)
                .updated(createdDateTime)
                .topic(topic)
                .build();
        given(postRepository.findById(1L)).willReturn(Optional.of(post));

        //when
        Post updatedPost = Post.builder().postId(1L).text("updated post").topic(topic).build();
        postService.update(updatedPost);

        //then
        assertThat(updatedPost.getCreated(), is(createdDateTime));
    }

    @Test
    public void givenPostDoesNotExist_whenDelete_thenThrowException(){
        //given
        given(postRepository.existsById(1L)).willReturn(false);

        //when
        Throwable throwable = assertThrows(NoSuchElementException.class, () -> postService.delete(1L));

        //then
        assertThat(throwable, is(notNullValue()));
    }
}
