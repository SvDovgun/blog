package com.luxoft.blog.post.web.controller;


import com.luxoft.blog.post.dto.DefaultCommentDto;
import com.luxoft.blog.post.dto.DefaultPostDto;
import com.luxoft.blog.post.dto.FullPost;
import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.service.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Validated
@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

    private final PostServiceImpl postService;

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostServiceImpl postService ) {
        this.postService = postService;
    }

    @GetMapping //(path = "/posts")
    public List<DefaultPostDto> getPosts(@RequestParam(name = "title", required = false) String title,
                                         @RequestParam(name = "sort", required = false) String sort) {
        LOGGER.info("Inside getPosts in PostController");
        List<Post> posts= null;

        if (Objects.nonNull(title) && Objects.isNull(sort)) {
            posts = postService.getPostByTitle(title);
        } else if (Objects.isNull(title) && Objects.nonNull(sort)) {
            posts = postService.getAllPostAndSortByTitle(sort);
        } else if (Objects.nonNull(title) && Objects.nonNull(sort)) {
            posts = postService.getPostByTitleAndSortByTitle(title, sort);
        } else {
            posts = postService.getAllPosts();
        }

        List<DefaultPostDto> allPosts = new ArrayList<>();
        for (Post post : posts) {
            allPosts.add(toPostWithoutCommentDto(post));
        }

        return allPosts;
    }

    @GetMapping(path = "{postId}" )
    public DefaultPostDto getPostById(@PathVariable("postId") Long postId)
            throws PostNotFoundException {
        LOGGER.info("Inside getPostById in PostController");

        return  toPostWithoutCommentDto(postService.getPostById(postId));

    }

    @GetMapping(path = "{postId}/full" )
    public FullPost getFullPostById(@PathVariable("postId") Long postId)
            throws PostNotFoundException {
        LOGGER.info("Inside getPostById in PostController");
        return  toFullPostDto(postService.getPostById(postId));
    }

    @PostMapping
    public void savePost(@Valid @RequestBody Post post) {
        LOGGER.info("Inside savePost in PostController");
        postService.savePost(post);

    }

    @DeleteMapping(path = "{postId}" )
    public void deletePost(@PathVariable("postId") Long postId) throws PostNotFoundException {
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
    public List<DefaultPostDto> getPostsWithStar() {
        LOGGER.info("Inside getPostsWithStar of PostController");
        List<Post> posts = postService.fetchPostsWithStar(true);
        List<DefaultPostDto> postsDto = new ArrayList<>();
        for (Post post : posts) {
            postsDto.add(toPostWithoutCommentDto(post));
        }
        return postsDto;
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

    DefaultPostDto toPostWithoutCommentDto(Post post){
        DefaultPostDto defaultPostDto = new DefaultPostDto();
        defaultPostDto.setId(post.getId());
        defaultPostDto.setTitle(post.getTitle());
        defaultPostDto.setContent(post.getContent());
        defaultPostDto.setStar(post.isStar());

        return defaultPostDto;
    }

    FullPost toFullPostDto(Post post){
        FullPost fullPost = new FullPost();
        fullPost.setId(post.getId());
        fullPost.setTitle(post.getTitle());
        fullPost.setContent(post.getContent());
        fullPost.setStar(post.isStar());

        List<Comment> comments = post.getComments(); //commentService.fetchComments(post.getId());
        List<DefaultCommentDto> allCommentsDto = new ArrayList<>();
        for (Comment comment : comments) {
            allCommentsDto.add(toDefaultCommentDto(comment));
        }
        fullPost.setComments(allCommentsDto);

        return fullPost;
    }

    DefaultCommentDto toDefaultCommentDto(Comment comment){
        DefaultCommentDto commentDto = new DefaultCommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setCreationDate(comment.getCreationDate());
        commentDto.setText(comment.getText());
        return commentDto;
    }
}
