package com.luxoft.blog.post.web.controller;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.entity.Tag;
import com.luxoft.blog.post.service.PostService;
import com.luxoft.blog.post.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagServiceMock;
    @MockBean
    private PostService postServiceMock;

    private Set<Post> posts;
    private Set<Post> posts2;

    @Test
    @DisplayName("test GET of all tags")
    void shouldReturnListOfTags() throws Exception {
        Post defaultPost = Post.builder()
                .title("Post GB")
                .content("test post record")
                .star(true)
                .id(1L)
                .comments(new ArrayList<>())
                .build();
        //posts = Set.of(defaultPost);

        Tag tag1 = Tag.builder()
                .id(1L)
                .name("Delphi")
                .posts(Set.of(defaultPost))
                .build();

        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Python")
                .posts(new HashSet<>())
                .build();

        Post postForList2 = Post.builder()
                .title("main post of second list")
                .content("test post for list")
                .star(true)
                .id(3L)
                .comments(new ArrayList<>())
                .build();
        //posts2 = Set.of(postForList2);

        Tag tag3 = Tag.builder()
                .id(3L)
                .name("Java")
                .posts(Set.of(postForList2))
                .build();

        Set<Tag> tags = Set.of(tag1, tag2, tag3);

        when(tagServiceMock.findAllTags()).thenReturn(tags);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/tags")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tag_id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Delphi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].tag_id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Python"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].tag_id").value("3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Java"))
                ;

        verify(tagServiceMock, times(1)).findAllTags();
    }

}