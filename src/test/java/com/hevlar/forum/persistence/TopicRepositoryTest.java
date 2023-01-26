package com.hevlar.forum.persistence;

import com.hevlar.forum.model.Topic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TopicRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TopicRepository repository;

    @Test
    @DisplayName("Topic title should be unique")
    void givenTopicTitleAlreadyPresent_WhenIdenticalTopicIsInserted_thenInsertionShouldFail(){
        //given
        Topic firstTopic = new Topic("First Topic");
        entityManager.persist(firstTopic);
        entityManager.flush();

        Topic retrievedTopic = repository.findAll().get(0);
        assertThat(retrievedTopic.getTitle(), is("First Topic"));

        //when
        Topic anotherFirstTopic = new Topic("First Topic");

        //then
        repository.save(anotherFirstTopic);
        assertThrows(DataIntegrityViolationException.class, () -> repository.flush());
    }

}
