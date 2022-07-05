package dev.nano.springboottesting.repository;

import dev.nano.springboottesting.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select * " +
                    "from posts where id = :id",
            nativeQuery = true
    )
    Optional<Post> findById(long id);

}
