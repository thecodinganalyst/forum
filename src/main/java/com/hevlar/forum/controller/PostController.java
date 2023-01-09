package com.hevlar.forum.controller;

import com.hevlar.forum.model.Post;
import com.hevlar.forum.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping(value = "/{topicId}/posts")
    public List<Post> list(@PathVariable("topicId") Long topicId){
        return postService.list(topicId);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post){
        return postService.create(post);
    }

    @GetMapping(value = "/post/{postId}")
    public Post get(@PathVariable("postId") Long postId){
        return postService.get(postId);
    }

    @PutMapping("/post")
    public Post update(@RequestBody Post post){
        return postService.update(post);
    }

    @DeleteMapping(value = "/post/{postId}")
    public void delete(@PathVariable("postId") Long postId){
        postService.delete(postId);
    }
}
