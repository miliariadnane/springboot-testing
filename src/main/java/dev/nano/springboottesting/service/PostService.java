package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> findPostById(long id);
    List<Post> findAllPosts();
    Post savePost(Post post);
    void deletePost(long id);
}
