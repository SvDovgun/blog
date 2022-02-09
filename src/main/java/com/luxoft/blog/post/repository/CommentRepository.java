package com.luxoft.blog.post.repository;

import com.luxoft.blog.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllCommentsByPostId(Long postId);

    //List<Comment> findComments;
}