package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts();

    void savePost(Post post);

    void deletePost(Long postId) throws PostNotFoundException;

    void updatePost(Long postId, Post newDataOfPost);

    List<Post> getPostByTitle(String title);

    List<Post> getAllPostAndSortByTitle(String sort);

    List<Post> getPostByTitleAndSortByTitle(String title,String sort);

    Post getPostById(Long postId) throws PostNotFoundException;

    List<Post> fetchPostsWithStar(boolean star);

    void setStarOfPost(Long postId, boolean starFlag) throws PostNotFoundException;
}
