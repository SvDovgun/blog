package com.luxoft.blog.post.web.controller;

import com.luxoft.blog.post.dto.CommentWithPostDto;
import com.luxoft.blog.post.dto.DefaultPostDto;
import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/posts")
public class CommentController {

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    private final CommentService commentService;

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);



    @PostMapping(path = "{postId}/comments" )
    public CommentWithPostDto saveCommentToPost(@PathVariable("postId") Long postId,
                                                @RequestBody Comment comment) throws PostNotFoundException {
        LOGGER.info("Inside saveCommentToPost of PostController");
        System.out.println(", postId =" + postId);
        Comment savedComment = commentService.saveComment(postId, comment);
        return toCommentWithPostDto(savedComment);
    }

    CommentWithPostDto toCommentWithPostDto(Comment comment){
        CommentWithPostDto commentWithPostDto = new CommentWithPostDto();
        commentWithPostDto.setCommentId(comment.getCommentId());
        commentWithPostDto.setCreationDate(comment.getCreationDate());
        commentWithPostDto.setText(comment.getText());

        Post post = comment.getPost();
        DefaultPostDto postWithoutCommentDto = new DefaultPostDto();
        postWithoutCommentDto.setId(post.getId());
        postWithoutCommentDto.setTitle(post.getTitle());
        postWithoutCommentDto.setContent(post.getContent());
        postWithoutCommentDto.setStar(post.isStar());

        commentWithPostDto.setDefaultPostDto(postWithoutCommentDto);
        return commentWithPostDto;
    }

    @GetMapping(path ="{postId}/comments" )
    public List<CommentWithPostDto> fetchAllComments(@PathVariable("postId") Long postId) {
        LOGGER.info("Inside fetchAllComments of PostController");
        List<Comment> comments = commentService.fetchComments(postId);

        List<CommentWithPostDto> allCommentsDto = new ArrayList<>();
        for (Comment comment : comments) {
            allCommentsDto.add(toCommentWithPostDto(comment));
        }
        return allCommentsDto;
    }

    @GetMapping(path ="{postId}/comments/{commentId}" )
    public CommentWithPostDto fetchComment(@PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId) {
        LOGGER.info("Inside fetchAllComments of PostController");
        Comment comment = commentService.fetchComment(commentId);

        return toCommentWithPostDto(comment);
    }
}
