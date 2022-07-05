package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.Post;
import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> findPostById(long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return Optional.of(post);
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePost(long id) {
        Optional<Post> post = Optional.of(postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found")));

        postRepository.deleteById(post.get().getId());
    }
}
