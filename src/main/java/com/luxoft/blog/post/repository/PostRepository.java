package com.luxoft.blog.post.repository;

import com.luxoft.blog.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //@Query("Select p from post p where p.title = ?1")
    //Optional<Post> findPostByTitle (String title);

}
