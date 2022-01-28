package com.luxoft.blog.post.web.controller;


import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

    private  final PostService postService;

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping //(path = "/posts")
    public List<Post> getPost(@RequestParam(name = "title", required = false) String title,
                              @RequestParam(name = "sort", required = false) String sort) {
        LOGGER.info("Inside getPost in PostController");
        if (Objects.nonNull(title) && Objects.isNull(sort)) {
               return postService.getPostByTitle(title);
        } else if (Objects.isNull(title) && Objects.nonNull(sort)) {
              return postService.getAllPostAndSortByTitle(sort);
        } else if (Objects.nonNull(title) && Objects.nonNull(sort)) {
             return postService.getPostByTitleAndSortByTitle(title, sort);
        }

        return  postService.getAllPosts();
    }

    @GetMapping(path = "{postId}" )
    public Post getPostById(@PathVariable("postId") Long postId)
            throws PostNotFoundException {
//        try {
//            return  postService.getPostById(postId);
//        } catch (PostNotFoundException e) {
//            throw new PostNotFoundException("Post is not available ");
//        }
//        return null;
        return  postService.getPostById(postId);

    }

    @PostMapping
    public void postNewPost (@Valid @RequestBody Post post) {
        LOGGER.info("Inside postNewPost in PostController");
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
