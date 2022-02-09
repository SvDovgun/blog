package com.luxoft.blog.post.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postServiceMock;

    @Autowired
    private ObjectMapper mapper;

    private Post post;

    @BeforeEach
    void setUp() {
//        post = Post.builder()
//                .title("Post GB")
//                .content("test post record")
//                .star(true)
//                .id(1L)
//                .comments(new ArrayList<>())
//                .build();
    }

    @Test
    void shouldSavePost() throws Exception {
        Post inputPost = Post.builder()
                .title("Post GB")
                .content("test post record")
                .star(true)
                .id(1L)
                .comments(new ArrayList<>())
                .build();


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(inputPost)))
                .andExpect(status().isOk());
        // TODO investigate issue in additional empty line in actual result
        //verify(postServiceMock, times(1)).savePost(inputPost);
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldReturnListOfPost() throws Exception {
        Post post1 = Post.builder()
                .title("Post GB")
                .content("Post GB record")
                .star(true)
                .id(1L)
                .comments(new ArrayList<>())
                .build();
        Post post2 = Post.builder()
                .title("test post")
                .content("test post record")
                .star(false)
                .id(2L)
                .comments(new ArrayList<>())
                .build();

        when(postServiceMock.getAllPosts())
                .thenReturn(List.of(post1, post2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Post GB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].star").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("test post"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].star").value("false"))
        ;

        verify(postServiceMock, times(1)).getAllPosts();
    }

    @Test
    void shouldReturnPostById() throws Exception {
        post = Post.builder()
                .title("Post GB")
                .content("test post record")
                .star(true)
                .id(1L)
                .comments(new ArrayList<>())
                .build();

        when(postServiceMock.getPostById(1L))
                .thenReturn(post);

        System.out.println("stop");
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Post GB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("test post record"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.star").value("true"));

        verify(postServiceMock, times(1)).getPostById(1L);
    }

    void checkThatTitleIsMandatory(){
        //via post
    }

}