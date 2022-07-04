package dev.nano.springboottesting.repository;

import dev.nano.springboottesting.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
