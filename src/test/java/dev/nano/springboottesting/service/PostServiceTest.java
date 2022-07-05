package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.Post;
import dev.nano.springboottesting.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService underTest;

    @MockBean
    private PostRepository postRepository;

    @Test
    void canGetAllPostsSuccessfully() {
        // When
        underTest.findAllPosts();
        // Then
        verify(postRepository).findAll();
    }

    @Test
    void canCreateNewPostSuccessfully() {
        // Given
        Post post = new Post(
                1L,
                "Post 1",
                "This is the description of post 1",
                new Date(),
                1
        );
        // When
        underTest.savePost(post);
        // Then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post postReturned = postArgumentCaptor.getValue();
        assertThat(postReturned).isEqualTo(post);
    }

    @Test
    void canDeletePost() {
        // Given
        long postId = 1L;
        Post postMock = new Post(
                postId,
                "Post 1",
                "This is the description of post 1",
                new Date(),
                1
        );
        given(postRepository.findById(postId)).willReturn(Optional.of(postMock));
        // When
        underTest.deletePost(postId);
        // Then
        verify(postRepository).deleteById(postId);
    }

    @Test
    void willThrowExceptionWhenDeletePostWithInvalidId() {
        // Given
        long postId = 1L;
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.deletePost(postId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Post not found");

        verify(postRepository, never()).deleteById(any());
    }

}
