package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Set<Tag> findAllTags();

    void saveTag(Post post, Tag tag);
}
