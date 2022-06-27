package dev.nano.springboottesting.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nano.springboottesting.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest // loads an embedded MongoDB instance
class UserRepositoryTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository underTest;

    private ObjectMapper mapper = new ObjectMapper();

    private static File DATA_JSON = Paths.get("src", "test", "resources", "data", "users.json").toFile();

    @BeforeEach
    void beforeEach() throws Exception {
        // Deserialize our JSON file to an array of users
        User[] objects = mapper.readValue(DATA_JSON, User[].class);

        // Load each user into MongoDB
        Arrays.stream(objects).forEach(mongoTemplate::save);
    }

    @AfterEach
    void afterEach() {
        // Drop users collection so we can start fresh
        mongoTemplate.dropCollection("Users");
    }

    @Test
    void itShouldFindUserById() {
        Optional<User> user = underTest.findById(1L);
        assertTrue(user.isPresent());
    }

    @Test
    void itShouldThrowExceptionWhenUserNotFoundById() {
        Optional<User> user = underTest.findById(50L);
        assertFalse(user.isPresent());
    }

    @Test
    void itShouldFindUserByUuid() {
        UUID uuid = UUID.fromString("b676ab38-f605-11ec-b939-0242ac120002");
        Optional<User> user = underTest.findUserByUuid(UUID.fromString(uuid.toString()));
        assertTrue(user.isPresent());
    }

    @Test
    void itShouldSaveUserSuccessfully() {
        User user = new User(
                1L, 
                UUID.randomUUID(), 
                "adnane", 
                "miliari", 
                "miliari.adnane@gmail.com",
                "123456789"
        );

        // save user to mongoDB database
        User savedUser = underTest.save(user);

        // retrieve user
        Optional<User> retrievedUser = underTest.findById(savedUser.getId());

        // validations
        assertNotNull(savedUser);
        retrievedUser.ifPresent(u -> {
            assertEquals(u.getId(), savedUser.getId());
            assertEquals(u.getFirstName(), savedUser.getFirstName());
            assertEquals(u.getLastName(), savedUser.getLastName());
            assertEquals(u.getEmail(), savedUser.getEmail());
            assertEquals(u.getPhone(), savedUser.getPhone());
        });
    }
}
