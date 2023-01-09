package com.hevlar.forum.service;

import com.hevlar.forum.model.Topic;
import com.hevlar.forum.persistence.TopicRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository){
        this.topicRepository = topicRepository;
    }

    public Topic create(Topic topic){
        return topicRepository.save(topic);
    }

    public List<Topic> list(){
        return topicRepository.findAll();
    }

    public Topic get(Long topicId){
        return topicRepository.findById(topicId).orElseThrow();
    }

    public Topic update(Topic topic){
        LocalDateTime created = topicRepository.findById(topic.getTopicId())
                .orElseThrow()
                .getCreated();
        topic.setCreated(created);
        return topicRepository.save(topic);
    }

    public void delete(Long topicId){
        if(!topicRepository.existsById(topicId)) throw new IllegalArgumentException();
        topicRepository.deleteById(topicId);
    }
}
