package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.entity.Tag;
import com.luxoft.blog.post.repository.PostRepository;
import com.luxoft.blog.post.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> findAllTags() {
       // Set<Tag> tags = tagRepository.findAllTags();
        List<Tag> tagsList = tagRepository.findAll();
        Set<Tag> tags = Set.copyOf(tagsList);
        return tags;
    }

    @Transactional
    @Override
    public void saveTag(Post postToLink, Tag tag)  {
        Optional<Tag> foundTag = tagRepository.findByName(tag.getName());

        if (foundTag.isPresent()) {
            Tag tagForUpdate = foundTag.get();
            Set<Post> linkedPosts = tag.getPosts();
            linkedPosts.add(postToLink);
            tagForUpdate.setPosts(linkedPosts);
            tagRepository.save(tagForUpdate);
        }
        else {
            Tag tagForSave = tag;
            tagForSave.setPosts(Set.of(postToLink));
            tagRepository.save(tagForSave);
        }

    }


}
