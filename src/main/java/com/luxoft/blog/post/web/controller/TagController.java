package com.luxoft.blog.post.web.controller;

import com.luxoft.blog.post.dto.DefaultTagDto;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.entity.Tag;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.service.PostService;
import com.luxoft.blog.post.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/posts")
public class TagController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    private final TagService tagService;
    private final PostService postService;

    @Autowired
    public TagController(TagService tagService, PostService postService) {
        this.tagService = tagService;
        this.postService = postService;
    }

    @GetMapping(path = "tags")
    public Set<DefaultTagDto> getSetOfTags() {
        LOGGER.info("Inside getSetOfTags in TagController");

        Set<Tag> tags = tagService.findAllTags();

        Set<DefaultTagDto> tagsSout = new HashSet<>();
        for (Tag tag : tags) {
            tagsSout.add(toDefaultTagDto(tag));
        }

        return tagsSout;
    }

    @PostMapping(path = "{postId}/tags")
    public void saveTagToPost(@PathVariable("postId") Long postId,
                              @RequestBody Tag tag) throws PostNotFoundException {
        LOGGER.info("Inside saveNewTag in TagController");
        Post post = postService.getPostById(postId);

        System.out.println("name" + tag.getName());

        tagService.saveTag(post, tag);
    }

    DefaultTagDto toDefaultTagDto(Tag tag){
        DefaultTagDto defaultTagDto = new DefaultTagDto();
        defaultTagDto.setTag_id(tag.getId());
        defaultTagDto.setName(tag.getName());

        return defaultTagDto;
    }
}
