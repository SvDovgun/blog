package com.luxoft.blog.post.service;

import com.luxoft.blog.post.repository.PostRepository;
import com.luxoft.blog.post.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        System.out.println("in blog method");
        return postRepository.findAll();
    }

    public void addPost(Post post) {
//        Optional<Post> postExisted = postRepository.findPostByTitle(post.getTitle());
//        if (postExisted.isPresent()){
//            throw  new IllegalStateException("post existed already");
//        }
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw  new IllegalStateException("Post by id " + postId + " doesn't exist");
        }
        postRepository.deleteById(postId);

    }

    @Transactional
    public void updatePost(Long postId, Post newDataOfPost) {
        Post existedPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalStateException("post with id =" + postId + "doesn't exist"));

        if (newDataOfPost.getTitle() != null &&
                newDataOfPost.getTitle().length() > 0 &&
                !Objects.equals(existedPost.getTitle(), newDataOfPost.getTitle())) {
            existedPost.setTitle(newDataOfPost.getTitle());
        }

        if (newDataOfPost.getContent() != null &&
                newDataOfPost.getContent().length() > 0 &&
                !Objects.equals(existedPost.getContent(), newDataOfPost.getContent())) {
            existedPost.setContent(newDataOfPost.getContent());
        }

    }
}
