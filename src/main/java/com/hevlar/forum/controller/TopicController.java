package com.hevlar.forum.controller;

import com.hevlar.forum.model.Topic;
import com.hevlar.forum.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    @GetMapping
    public List<Topic> listTopics(){
        return topicService.list();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Topic createTopic(@RequestBody Topic topic){
        return topicService.create(topic);
    }

    @GetMapping(value = "/{topicId}")
    public Topic getTopic(@PathVariable("topicId") Long topicId){
        return topicService.get(topicId);
    }

    @PutMapping
    public Topic updateTopic(@RequestBody Topic topic){
        return topicService.update(topic);
    }

    @DeleteMapping(value = "/{topicId}")
    public void deleteTopic(@PathVariable("topicId") Long topicId){
        topicService.delete(topicId);
    }
}
