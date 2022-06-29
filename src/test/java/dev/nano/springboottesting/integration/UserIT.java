package dev.nano.springboottesting.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @Test
    void canCreateNewUser() throws Exception {

        // Setup mocked service
        User mockUser = generateMockedUser();

        // Execute the POST request
        ResultActions resultActions = mockMvc
                .perform(post("/api/v1/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser))
                );

        // Verify the response
        resultActions.andExpect(status().isCreated());
        List<User> users = userRepository.findAll();
        assertThat(users)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "uuid") // because id and uuid are randomly generated
                .contains(mockUser);
    }

    @Test
    @Disabled("Not yet completed")
    void canDeleteUser() throws Exception {

        // Setup mocked service
        UUID uuid = UUID.fromString("4ab7ee3e-cd46-40b1-b1f2-3931e34e7c17");
        User mockUser = new User(
                1L,
                uuid,
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );

        // Execute requests
        mockMvc.perform(post("/api/v1/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUser)))
                .andExpect(status().isCreated())
                .andReturn();

        MvcResult getUsersResult = mockMvc.perform(get("/api/v1/users/all")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        // Verify the response

        /* convert result to string */
        String contentAsString = getUsersResult.getResponse().getContentAsString();

        List<User> users = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        users.stream()
                .filter(user -> user.getUuid().equals(uuid))
                .map(User::getUuid)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "User not found"));

        ResultActions resultActions = mockMvc
                .perform(delete("/api/v1/users/delete/" + uuid  ));

        resultActions.andExpect(status().isOk());
        assertThat(users)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "uuid")
                .doesNotContain(mockUser);
    }


    private User generateMockedUser() {

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        String name = String.format("%s %s",
                firstName,
                lastName
        );

        String email = String.format("%s@contact.com",
                StringUtils.trimAllWhitespace(name.trim().toLowerCase()));

        User mockUser = new User(
                faker.number().randomNumber(),
                UUID.randomUUID(),
                firstName,
                lastName,
                email,
                faker.phoneNumber().phoneNumber()
        );

        return mockUser;
    }
}
