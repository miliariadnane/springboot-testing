package dev.nano.springboottesting.repository;

import dev.nano.springboottesting.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findUserById(Long id);
    Optional<User> findUserByUuid(UUID uuid);
}
