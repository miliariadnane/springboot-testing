package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Optional<User> findById(long id);
    List<User> findAllUsers();
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(UUID uuid);
}
