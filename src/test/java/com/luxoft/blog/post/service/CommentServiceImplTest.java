package com.luxoft.blog.post.service;

import com.luxoft.blog.post.entity.Comment;
import com.luxoft.blog.post.entity.Post;
import com.luxoft.blog.post.repository.CommentRepository;
import com.luxoft.blog.post.repository.PostRepository;
import com.sun.xml.bind.v2.TODO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepositoryMock;
    @Mock
    private PostRepository postRepositoryMock;

    @Test
    @DisplayName("check the save process of comment")
    // TODO
    void testSaveComment() {
    }

    @Test
    @DisplayName("Find and display comments by post_id")
    void testFetchComments() {
        CommentServiceImpl commentService =  new CommentServiceImpl(commentRepositoryMock, postRepositoryMock);

        List<Comment> comments = new ArrayList<>();
        Post post = new Post(1L,"post subject","content of post",true);

        Comment firstComment = Comment.builder()
                .commentId(1L)
                .text("First comment text")
                .creationDate(LocalDateTime.now())
                .post(post)
                .build();
        comments.add(firstComment);

        Comment secondComment = Comment.builder()
                .commentId(2L)
                .text("Second comment text")
                .creationDate(LocalDateTime.now())
                .post(post)
                .build();
        comments.add(secondComment);

        Comment thirdComment = Comment.builder()
                .commentId(3L)
                .text("Third comment text")
                .creationDate(LocalDateTime.now())
                .post(post)
                .build();
        comments.add(thirdComment);

        Mockito.when(commentRepositoryMock.findAllCommentsByPostId(1L)).thenReturn(comments);

        List<Comment> actualComments = commentService.fetchComments(1L);
        assertNotNull(actualComments);
        assertEquals(3, actualComments.size());
        assertEquals("Second comment text", actualComments.get(1).getText());
        assertEquals(thirdComment, actualComments.get(2));
    }

    @Test
    @DisplayName("find specified comment of post")
    void testFetchComment() {
        CommentServiceImpl commentService =  new CommentServiceImpl(commentRepositoryMock, postRepositoryMock);

        Post post = new Post(1L,"post subject","content of post",true);
        LocalDateTime now = LocalDateTime.now();

        Comment specifiedComment = Comment.builder()
                .commentId(1L)
                .text("Specified comment text for specified post")
                .creationDate(now)
                .post(post)
                .build();

        Mockito.when(commentRepositoryMock.findCommentByCommentIdAndAndPost(1L, post.getId())).thenReturn(Optional.ofNullable(specifiedComment));

        Comment actualComment = commentService.fetchComment(1L, 1L);

        assertEquals("Specified comment text for specified post", actualComment.getText());
        assertEquals(1L, actualComment.getPost().getId());
        assertEquals(now, actualComment.getCreationDate());
        assertInstanceOf(Post.class, actualComment.getPost());


    }
}