package dev.nano.springboottesting.service;

import dev.nano.springboottesting.entity.User;
import dev.nano.springboottesting.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService underTest;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findUserById Success")
    void itShouldFindUserByIdSuccessfully() {
        // Setup our mock
        User mockUser = new User(
                1L,
                UUID.randomUUID(),
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );
        doReturn(Optional.of(mockUser)).when(userRepository).findUserById(1L);

        // Execute the service call
        Optional<User> returnedUser = underTest.findById(1L);

        // Assert the response
        assertTrue(returnedUser.isPresent(), "User should be present");
        assertSame(returnedUser.get(), mockUser, "User should be the same");
    }

    @Test
    @DisplayName("Test findUserById Success with BDD-Mockito")
    void itShouldFindUserByIdSuccessfullyWithBDDMockito() {
        // Given
        User mockUser = new User(
                1L,
                UUID.randomUUID(),
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );

        given(userRepository.findUserById(1L)).willReturn(Optional.of(mockUser));

        // When
        Optional<User> returnedUser = underTest.findById(1L);

        // Then
        assertTrue(returnedUser.isPresent(), "User should be present");
        assertSame(returnedUser.get(), mockUser, "User should be the same");
    }

    @Test
    @DisplayName("Test findUserById Not Found")
    void itShouldThrowExceptionWhenUserNotFound() {
        // Setup our mock
        long id = 99L;
        doReturn(Optional.empty()).when(userRepository).findUserById(id);

        // Execute and Assert the response
        assertThatThrownBy(() -> underTest.findById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User not found");
        verify(userRepository, never()).findById(any());
        // verify(userRepository, times(0)).findById(id);
    }

    @Test
    @DisplayName("Test findAllUsers Success")
    void itShouldFindAllUsersSuccessfully() {
        // Setup our mock
        User mockUser1 = new User(
                1L,
                UUID.randomUUID(),
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );
        User mockUser2 = new User(
                2L,
                UUID.randomUUID(),
                "abdelaati",
                "aatouan",
                "abd.abdelaati@gmail.com",
                "123456789"
        );

        doReturn(Arrays.asList(mockUser1, mockUser2)).when(userRepository).findAll();

        // Execute the service call
        List<User> returnedUsers = underTest.findAllUsers();

        // Assert the response
        assertEquals(2, returnedUsers.size(), "findAll Should have 2 users");
    }

    @Test
    @DisplayName("Test save user Success")
    void itShouldSaveUserSuccessfully() {
        // Setup our mock
        User mockUser = new User(
                1L,
                UUID.randomUUID(),
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );

        doReturn(mockUser).when(userRepository).save(mockUser);

        // Execute the service call
        underTest.saveUser(mockUser);

        // Assert the response
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        assertSame(mockUser, userArgumentCaptor.getValue(), "User should be the same");
        assertThat(userArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(mockUser)
                .ignoringFields("Uuid");
    }

    @Test
    @DisplayName("Test delete user Success")
    void itCanDeleteUser() {
        // Setup our mock
        UUID uuid = UUID.fromString("e756bb6b-dd49-40b0-b0f2-a1d2f24adc7d");
        User mockUser = new User(
                1L,
                uuid,
                "adnane",
                "miliari",
                "miliari.adnane@gmail.com",
                "123456789"
        );

        doReturn(Optional.of(mockUser)).when(userRepository).findUserByUuid(uuid);

        // Execute the service call
        underTest.deleteUser(uuid);

        // Assert the response
        verify(userRepository, times(1)).delete(mockUser);
    }
}
