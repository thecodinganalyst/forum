package com.hevlar.forum.controller;

import com.hevlar.forum.model.Topic;
import com.hevlar.forum.service.TopicService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(origins = {"*"})
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
        try{
            return topicService.get(topicId);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public Topic updateTopic(@RequestBody Topic topic){
        try{
            return topicService.update(topic);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/{topicId}")
    public void deleteTopic(@PathVariable("topicId") Long topicId){
        try{
            topicService.delete(topicId);
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
