package com.luxoft.blog.post.service;

import com.luxoft.blog.BlogApplication;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    private Post post;

    @BeforeEach
    void setUp() {
        post = Post.builder()
                .id(1L)
                .title("post Test")
                .content("test of post in Service layer")
                .build();

        Mockito.when(postRepository.findByTitle("post Test"))
                       .thenReturn(post);

    }

    @Test
    void whenValidPostTitle_ThenPostShouldFound(){
        String postTitle = "post Test";
        List<Post> found = postService.getPostByTitle(postTitle);
        System.out.println(found.size());

      //  assertEquals(postTitle, found.get(0).getTitle());

    }



    @Test
    void shouldCreateMockMvc() {

        System.out.println("test for SERVICE!!!!");
    }

    @Test
    void shouldReturnListOfPost() throws Exception {
        System.out.println("test?");
    }



}