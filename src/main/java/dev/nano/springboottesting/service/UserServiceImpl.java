package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.of(userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        user.setUuid(UUID.randomUUID());
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = new User();
        updatedUser.setId(user.getId());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPhone(user.getPhone());
        return userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(UUID uuid) {
        Optional<User> user = Optional.of(userRepository.findUserByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("User not found")));

        userRepository.delete(user.get());
    }
}
