package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.repository.CommentRepository;
import com.luxoft.blog.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private final PostService postService;

    public CommentService(CommentRepository commentRepository, PostService postService) {
        this.commentRepository = commentRepository;
        this.postService = postService;
    }


    public Comment saveComment(Long postId, Comment comment) throws PostNotFoundException {
        System.out.println("in saveComment, postId" + postId);
        Post postFromDB = postService.getPostById(postId);
        System.out.println(postFromDB);
        comment.setPost(postFromDB);

        Comment commentToDB = commentRepository.save(comment);
        return commentToDB;
    }

    public List<Comment> fetchComments(Long postId) {
        List<Comment> comments =commentRepository.findAllCommentsByPostId(postId);
        return comments;
    }

    public Comment fetchComment(Long commentId) {
        Optional<Comment> commentById = commentRepository.findById(commentId);

        if (!commentById.isPresent()){
            System.out.println("Searched comment are not found");
        }
        return commentById.get();
    }
}
