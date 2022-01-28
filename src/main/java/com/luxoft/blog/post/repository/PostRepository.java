package com.luxoft.blog.post.repository;

import com.luxoft.blog.post.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //@Query("Select p from post p where p.title = ?1")
    //Optional<Post> findPostByTitle (String title);
    Optional<Post> findPostById(Long id);

    Post findByTitle(String title);

    List<Post> findByTitleContainingIgnoreCase(String title);

    List<Post> findByTitleContainingIgnoreCaseOrderByTitle(String title);

    List<Post> findByTitleContainingIgnoreCaseOrderByTitleDesc(String title);

    List<Post> getAllByOrderByTitle();

    List<Post> getAllByOrderByTitleDesc();
}
