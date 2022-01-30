package com.luxoft.blog.post.web.controller;


import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        LOGGER.info("Inside getPostById in PostController");
//        try {
//            return  postService.getPostById(postId);
//        } catch (PostNotFoundException e) {
//            throw new PostNotFoundException("Post is not available ");
//        }
//        return null;
        return  postService.getPostById(postId);

    }

    @PostMapping
    public void savePost(@Valid @RequestBody Post post) {
        LOGGER.info("Inside savePost in PostController");
        postService.savePost(post);

    }

    @DeleteMapping(path = "{postId}" )
    public void deletePost(@PathVariable("postId") Long postId) {
        LOGGER.info("Inside deletePost of PostController");
        postService.deletePost(postId);

    }

    @PutMapping(path = "{postId}" )
    public void updatePost(@PathVariable("postId") Long postId,
                           @RequestBody Post post) {
        LOGGER.info("Inside updatePost of PostController");
        postService.updatePost(postId, post);

    }

    @GetMapping(path = "/star" )
    public List<Post> getPostsWithStar() {
        LOGGER.info("Inside getPostsWithStar of PostController");
        List<Post> posts = postService.fetchPostsWithStar(true);
        return posts;
    }

    @PutMapping(path = "{postId}/star" )
    public void setStarOfPost(@PathVariable("postId") Long postId) throws PostNotFoundException {
        LOGGER.info("Inside setStarOfPost of PostController");
        postService.setStarOfPost(postId, true);
    }

    @DeleteMapping(path = "{postId}/star" )
    public void deleteStarOfPost(@PathVariable("postId") Long postId) throws PostNotFoundException {
        LOGGER.info("Inside deleteStarOfPost of PostController");
        postService.setStarOfPost(postId, false);
    }
}
