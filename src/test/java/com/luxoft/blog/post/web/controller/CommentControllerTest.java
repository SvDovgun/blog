package com.luxoft.blog.post.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.service.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentServiceImpl commentServiceMock;

    Post post = Post.builder()
             .id(1L)
             .title("test post")
             .content("Content of the post")
             .star(true)
             .comments(new ArrayList<>())
              .build();
    Post post2 = Post.builder()
            .id(2L)
            .title("test post2")
            .content("Content of the post2")
            .star(false)
            .comments(new ArrayList<>())
            .build();

    @Test
    @DisplayName("Successfully save comment")
    void testSaveCommentToPost() throws Exception  {
        Comment comment1 = Comment.builder()
                .commentId(1L)
                .text("First comment")
                .creationDate(LocalDateTime.now())
                .post(post)
                .build();

        when(commentServiceMock.saveComment(post.getId(), comment1)).thenReturn(comment1);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts/{id}/comments", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(comment1)))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Find and display all comments of specified post")
    void testFetchAllComments() throws Exception {
        List<Comment> comments = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        Comment firstComment = Comment.builder()
                .commentId(1L)
                .text("First comment")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(firstComment);

        Comment secondComment = Comment.builder()
                .commentId(2L)
                .text("Second comment")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(secondComment);

        Comment thirdComment = Comment.builder()
                .commentId(3L)
                .text("Third comment")
                .creationDate(now)
                .post(post)
                .build();
        comments.add(thirdComment);

        when(commentServiceMock.fetchComments(1L)).thenReturn(comments);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{id}/comments", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text").value("First comment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].text").value("Second comment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].text").value("Third comment"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").doesNotExist());
        verify(commentServiceMock, times(1)).fetchComments(1L);
    }

    @Test
    @DisplayName("Find and display specified comment")
    void testFetchComment() throws Exception {
        Comment comment = Comment.builder()
                .commentId(1L)
                .text("First comment")
                .creationDate(LocalDateTime.now())
                .post(post2)
                .build();

        when(commentServiceMock.fetchComment( post2.getId(), comment.getCommentId())).thenReturn(comment);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/api/v1/posts/{postId}/comments/{commentId}", post2.getId(), comment.getCommentId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.commentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("First comment"));
        verify(commentServiceMock, times(1)).fetchComment(post2.getId(), comment.getCommentId());
    }

    @Test
    @DisplayName("Attempt to save empty comment returns Bad request error")
    void testSaveCommentWhenCommentIsNull() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/api/v1/posts/{id}/comments", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());

    }
}