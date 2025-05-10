package services_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserExistsException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.RegisterService;
import com.example.eksamens_vm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        // Override behavior for testing without mocks
        registerService = new RegisterService() {
            {
                this.userService = new UserService() {
                    @Override
                    public boolean userExists(String username) {
                        return "existingUser".equals(username);
                    }
                };
                this.jsonService = new JsonService() {
                    @Override
                    public <T> void save(T object, String filename, Class<T> type) {
                        // Simulate save without actual file I/O
                        System.out.println("Simulated saving user to: " + filename);
                    }
                };
            }
        };
    }

    private User createUser(String username, String password) {
        return new User(1, username, password, UserRole.STUDENT, Collections.emptyList());
    }

    @Test
    void testSuccessfulRegistration() {
        User user = createUser("newUser", "securePassword");

        assertDoesNotThrow(() -> registerService.register(user));
    }

    @Test
    void testRegistrationFailsWhenUserExists() {
        User user = createUser("existingUser", "somePassword");

        UserExistsException ex = assertThrows(UserExistsException.class, () -> {
            registerService.register(user);
        });

        assertEquals("user already exists!", ex.getMessage());
    }

    @Test
    void testRegistrationFailsWhenFieldsAreEmpty() {
        User user = createUser("", "");

        InputFieldEmptyException ex = assertThrows(InputFieldEmptyException.class, () -> {
            registerService.register(user);
        });

        assertEquals("you must fill all fields!", ex.getMessage());
    }

    @Test
    void testRegistrationFailsWhenPasswordIsEmpty() {
        User user = createUser("validUsername", "");

        InputFieldEmptyException ex = assertThrows(InputFieldEmptyException.class, () -> {
            registerService.register(user);
        });

        assertEquals("you must fill all fields!", ex.getMessage());
    }

    @Test
    void testRegistrationFailsWhenUsernameIsEmpty() {
        User user = createUser("", "validPassword");

        InputFieldEmptyException ex = assertThrows(InputFieldEmptyException.class, () -> {
            registerService.register(user);
        });

        assertEquals("you must fill all fields!", ex.getMessage());
    }
}
