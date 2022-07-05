package dev.nano.springboottesting.repository;

import dev.nano.springboottesting.entity.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfPostFoundByIdSuccessfully() {
        // Given
        long id = 1L;
        Post post = new Post(
                id,
                "Post 1",
                "This is the description of post 1",
                new Date(),
                1
        );

        underTest.save(post);
        // When
        Optional<Post> postReturned = underTest.findById(id);
        // Then
        assertThat(postReturned).isPresent().hasValueSatisfying(p -> {
            assertThat(p).usingRecursiveComparison().isEqualTo(post);
        });
    }

    @Test
    void itShouldCheckIfPostNotFoundById() {
        // Given
        long id = 99L;
        // When
        Optional<Post> postReturned = underTest.findById(id);
        // Then
        assertThat(postReturned).isNotPresent();
    }

}
