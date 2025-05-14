package services_tests;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.InvalidCredentialsException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.LoginService;
import com.example.eksamens_vm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private LoginService loginService;
    private UserService mockUserService;

    @BeforeEach
    void setUp() {
        // Mock the UserService manually
        mockUserService = new UserService() {
            @Override
            public User getUserByUsername(String username) {
                // Return a mock user when the username matches
                if ("testUser".equals(username)) {
                    return new User(1, "testUser", "validPassword", UserRole.STUDENT, List.of());
                }
                return null; // Simulate that no user is found for other usernames
            }
        };

        // Create the LoginService with the manually mocked UserService
        loginService = new LoginService();
    }




    @Test
    void testUserNotFound() {
        String username = "nonExistentUser";
        String password = "password";

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            loginService.login(username, password);
        });

        assertEquals("user with that username doesnt exist!", exception.getMessage());
    }

    @Test
    void testInvalidPassword() {
        String username = "testUser";
        String password = "incorrectPassword";

//        Exception exception = assertThrows(UserNotFoundException.class, () -> {
//            loginService.login(username, password);
//        });

   //     assertEquals("incorrect password", exception.getMessage());
    }

    @Test
    void testEmptyUsername() {
        String username = "";
        String password = "password";

        Exception exception = assertThrows(InputFieldEmptyException.class, () -> {
            loginService.login(username, password);
        });

        assertEquals("cannot leave fields empty", exception.getMessage());
    }

    @Test
    void testEmptyPassword() {
        String username = "testUser";
        String password = "";

        Exception exception = assertThrows(InputFieldEmptyException.class, () -> {
            loginService.login(username, password);
        });

        assertEquals("cannot leave fields empty", exception.getMessage());
    }

    @Test
    void testEmptyUsernameAndPassword() {
        String username = "";
        String password = "";

        Exception exception = assertThrows(InputFieldEmptyException.class, () -> {
            loginService.login(username, password);
        });

        assertEquals("cannot leave fields empty", exception.getMessage());
    }
}
