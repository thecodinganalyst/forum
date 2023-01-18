package com.hevlar.forum.persistence;

import com.hevlar.forum.model.Post;
import com.hevlar.forum.model.Topic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void givenTopicHasPosts_whenFindAllByTopic_TopicId_thenReturnTopics(){
        //given
        Topic topic = new Topic("First Topic");
        entityManager.persist(topic);
        entityManager.flush();

        Post post1 = new Post("First Post", topic);
        Post post2 = new Post("Second Post", topic);
        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.flush();

        //when
        List<Post> postList = postRepository.findAllByTopic_TopicId(topic.getTopicId());

        //then
        assertThat(postList.size(), is(2));
        assertThat(postList, containsInAnyOrder(post1, post2));
    }

}
