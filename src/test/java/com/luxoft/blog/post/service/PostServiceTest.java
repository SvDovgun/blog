package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.error.PostNotFoundException;
import com.luxoft.blog.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepositoryMock;

    private Post post;

    private List<Post> posts ;

    private final String postTitle = "Films";

    @BeforeEach
    void setUp() {
        Post post1 = Post.builder()
                .id(1L)
                .title(postTitle)
                .content("Interesting movie")
                .star(true)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title(postTitle)
                .content("good, good, good film")
                .star(false)
                .build();

        //List<Post>
                posts = List.of(post1, post2);

        Mockito.when(postRepositoryMock.findByTitleContainingIgnoreCase(postTitle))
                .thenReturn(posts);

    }

    @Test
    void whenValidPostTitle_ThenPostsShouldFound(){
        // when
        List<Post> foundPosts = postService.getPostByTitle(postTitle);
        // then
        assertEquals(2, foundPosts.size());
    }

    @Test
    void whenInvalidPostTitle_ThenNoPostsShouldFound(){
        // when
        List<Post> foundPosts = postService.getPostByTitle("invalid title");
        // then
        assertEquals(0, foundPosts.size());
    }

    @Test
    void whenValidPostId_ThenPostShouldFound() throws PostNotFoundException {
        // prepare
        Post firstPost = Post.builder()
                .id(1L)
                .title(postTitle)
                .content("Interesting movie")
                .star(true)
                .build();

        Mockito.when(postRepositoryMock.findPostById(1L))
                .thenReturn(Optional.ofNullable(firstPost));
        // when
        Post postById = postService.getPostById(1L);
        // then
        assertEquals(1L, postById.getId());
        assertNotNull(postById);
        assertEquals(postTitle, postById.getTitle());
        assertEquals("Interesting movie", postById.getContent());
        assertTrue(postById.isStar());
        verify(postRepositoryMock, times(1)).findPostById(1L);
    }

    @Test
    void whenInvalidPostId_ThenException()  {
        Mockito.when(postRepositoryMock.findPostById(546L))
                .thenThrow(new IllegalArgumentException());

    }

    @Test
    void whenRequiredPostWithStar_ThenPostsShouldFound(){
        Post post3 = Post.builder()
                .id(3L)
                .title("Other film")
                .content("another one  movie")
                .star(true)
                .build();
        Post post4 = Post.builder()
                .id(4L)
                .title("Cinema")
                .content("Interesting movie")
                .star(true)
                .build();

        List<Post> postsSet = List.of(post3, post4);
        Mockito.when(postRepositoryMock.getAllByStarEquals(true))
                .thenReturn(postsSet);

        List<Post> foundPosts = postService.fetchPostsWithStar(true);

        assertEquals(2, foundPosts.size());
    }

}