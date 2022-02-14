package com.luxoft.blog.post.service;

import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.repository.PostRepository;
import com.luxoft.blog.post.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        System.out.println("in blog method");
        return postRepository.findAll();
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) throws PostNotFoundException {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw  new PostNotFoundException("Post by id " + postId + " doesn't exist");
        }
        postRepository.deleteById(postId);

    }
    @Transactional
    @Override
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

    @Override
    public List<Post> getPostByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Post> getAllPostAndSortByTitle(String sort) {
        if (sort.equalsIgnoreCase("desc")){
            return postRepository.getAllByOrderByTitleDesc();
        }
        return postRepository.getAllByOrderByTitle();
    }

    @Override
    public List<Post> getPostByTitleAndSortByTitle(String title,String sort) {
        if (sort.equalsIgnoreCase("desc")){
            return postRepository.findByTitleContainingIgnoreCaseOrderByTitleDesc(title);
        }
        return postRepository.findByTitleContainingIgnoreCaseOrderByTitle(title);
    }

    @Override
    public Post getPostById(Long postId) throws PostNotFoundException {
        Optional<Post> postById = postRepository.findPostById(postId);

        if (!postById.isPresent()){
            throw new PostNotFoundException("Searched post are not found");
        }
        return postById.get();
    }

    @Override
    public List<Post> fetchPostsWithStar(boolean star) {
        return postRepository.getAllByStarEquals(star);
    }

    @Override
    public void setStarOfPost(Long postId, boolean starFlag) throws PostNotFoundException {
        Optional<Post> postById = postRepository.findPostById(postId);

        if (postById.isPresent()){
            postById.get().setStar(starFlag);
            postRepository.save(postById.get());

        } else {
            throw new PostNotFoundException("Searched post are not found");
        }
    }
}
