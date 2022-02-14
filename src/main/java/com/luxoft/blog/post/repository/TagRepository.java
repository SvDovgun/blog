package com.luxoft.blog.post.repository;

import com.luxoft.blog.post.entity.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query(value = "SELECT distinct t.name FROM tags t ORDER BY asc", nativeQuery = true)
    Set<Tag> findAllTags();

    List<Tag> findAll();

    Optional<Tag> findByName(String name);
}
