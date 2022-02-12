package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.error.PostNotFoundException;

import java.util.List;

public interface CommentService {

    Comment saveComment(Long postId, Comment comment) throws PostNotFoundException;

    List<Comment> fetchComments(Long postId);

    Comment fetchComment(Long postId, Long commentId);
}
