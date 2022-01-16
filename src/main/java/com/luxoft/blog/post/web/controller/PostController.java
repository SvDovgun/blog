package com.luxoft.blog.post.web.controller;


import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

    private  final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping //(path = "/posts")
    public List<Post> getPost() {
      return  postService.getPosts();
    }

    @PostMapping
    public void postNewPost (@RequestBody Post post) {
        postService.addPost(post);

    }

    @DeleteMapping(path = "{postId}" )
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);

    }

    @PutMapping(path = "{postId}" )
    public void updatePost(@PathVariable("postId") Long postId,
                           @RequestBody Post post) {
        postService.updatePost(postId, post);

    }
}
