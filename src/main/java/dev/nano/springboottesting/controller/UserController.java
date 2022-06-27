package dev.nano.springboottesting.controller;

import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    private static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = Optional.ofNullable(userService.findById(id))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new ResponseEntity(user, HttpStatus.OK);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        User newUser = userService.saveUser(user);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return new ResponseEntity(USER_DELETED_SUCCESSFULLY, HttpStatus.OK);
    }
}
