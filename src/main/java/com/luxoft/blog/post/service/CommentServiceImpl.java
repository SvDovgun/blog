package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.repository.CommentRepository;
import com.luxoft.blog.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    public Comment saveComment(Long postId, Comment comment) throws PostNotFoundException {
        System.out.println("in saveComment, postId" + postId);
        Optional<Post> postFromDB = postRepository.findPostById(postId);
        System.out.println(postFromDB);
        comment.setPost(postFromDB.get());

        Comment commentToDB = commentRepository.save(comment);
        return commentToDB;
    }

    public List<Comment> fetchComments(Long postId) {
        List<Comment> comments =commentRepository.findAllCommentsByPostId(postId);
        return comments;
    }

    public Comment fetchComment(Long postId, Long commentId) {
        Optional<Comment> commentById = commentRepository.findCommentByCommentIdAndAndPost(commentId, postId);

        if (!commentById.isPresent()){
            System.out.println("Searched comment are not found");
        }
        return commentById.get();
    }
}
