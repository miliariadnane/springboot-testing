package dev.nano.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/user/1 - Success")
    void itShouldGetUserByIdSuccessfully() throws Exception {
        // Setup mocked service
        UUID uuid = UUID.fromString("e756bb6b-dd49-40b0-b0f2-a1d2f24adc7d");
        long id = 1L;
        User mockUser = new User(
                id,
                uuid,
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );
        doReturn(Optional.of(mockUser)).when(userService).findById(id);

        // Execute the GET request
        mockMvc.perform(get("/api/v1/users/{id}", id))

            // Validate response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            // Validate the response fields
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.uuid").value(uuid.toString()))
            .andExpect(jsonPath("$.firstName").value("adnane"))
            .andExpect(jsonPath("$.lastName").value("miliari"))
            .andExpect(jsonPath("$.email").value("miliari.adnane@gmail.com"))
            .andExpect(jsonPath("$.phone").value("123456789"));
    }

    @Test
    @DisplayName("GET /api/v1/users/all - Success")
    void itShouldGetAllUsersSuccessfully() throws Exception {
        // Setup mocked service
        UUID uuid1 = UUID.fromString("e756bb6b-dd49-40b0-b0f2-a1d2f24adc7d");
        UUID uuid2 = UUID.fromString("0f3401b0-1b3c-417c-8160-ef7a4890289d");

        User mockUser1 = new User(
                1L,
                uuid1,
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );

        User mockUser2 = new User(
                2L,
                uuid2,
                "rizki",
                "dadda",
                "dadda.rizki@gmail.com",
                "123456789"
        );
        List<User> listUsers =
                doReturn(Arrays.asList(mockUser1, mockUser2)).when(userService).findAllUsers();

        // Execute the GET request
        mockMvc.perform(get("/api/v1/users/all"))

            // Validate response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

            // Validate the response fields
            .andExpect(jsonPath("$.size()", is(2)))
            .andExpect(jsonPath("$[0].id").value(is(1)))
            .andExpect(jsonPath("$[1].id").value(is(2)))
            .andExpect(jsonPath("$[0].uuid").value(is(uuid1.toString())))
            .andExpect(jsonPath("$[1].uuid").value(is(uuid2.toString())))
            .andExpect(jsonPath("$[0].email").value(is(mockUser1.getEmail())))
            .andExpect(jsonPath("$[1].email").value(is(mockUser2.getEmail())));
    }

    @Test
    @DisplayName("POST /api/v1/users/add - Success")
    void itCanCreateNewUserSuccessfully() throws Exception {
        // Setup mocked service
        UUID uuid = UUID.fromString("e756bb6b-dd49-40b0-b0f2-a1d2f24adc7d");
        User mockUser = new User(
                1L,
                uuid,
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );
        doReturn(mockUser).when(userService).saveUser(any());

        mockMvc.perform(post("/api/v1/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mockUser)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.uuid", is(uuid.toString())))
                .andExpect(jsonPath("$.firstName", is("adnane")))
                .andExpect(jsonPath("$.lastName", is("miliari")))
                .andExpect(jsonPath("$.email", is("miliari.adnane@gmail.com")))
                .andExpect(jsonPath("$.phone", is("123456789")));
    }

    @Test
    @DisplayName("POST /api/v1/users/delete/1 - Success")
    void itShouldDeleteUserSuccessfully() throws Exception {
        // Setup mocked service
        UUID uuid = UUID.fromString("e756bb6b-dd49-40b0-b0f2-a1d2f24adc7d");

        // Execute the POST request
        MvcResult result = mockMvc.perform(delete("/api/v1/users/delete/{id}", uuid.toString()))

            // Validate response code and content type
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        assertEquals("User deleted successfully", responseBody);
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
