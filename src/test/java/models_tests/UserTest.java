package models_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1, "alice", "secret", UserRole.STUDENT, List.of(10, 20, 30));
    }

    @Test
    void constructorAndGetters_shouldInitializeAllFields() {
        assertEquals(1, user.getId(),           "ID must match constructor");
        assertEquals("alice", user.getUsername(), "Username must match constructor");
        assertEquals("secret", user.getPassword(), "Password must match constructor");
        assertEquals(UserRole.STUDENT, user.getUserType(), "UserRole must match constructor");
        assertEquals(List.of(10, 20, 30), user.getRooms(), "Rooms list must match constructor");
    }

    @Test
    void setters_shouldUpdateFieldsCorrectly() {
        user.setId(42);
        user.setUsername("bob");
        user.setPassword("hunter2");
        user.setUserType(UserRole.TEACHER);
        user.setRooms(List.of(5, 6));

        assertAll("mutations",
                () -> assertEquals(42, user.getId()),
                () -> assertEquals("bob", user.getUsername()),
                () -> assertEquals("hunter2", user.getPassword()),
                () -> assertEquals(UserRole.TEACHER, user.getUserType()),
                () -> assertEquals(List.of(5, 6), user.getRooms())
        );
    }

    @Test
    void toString_shouldContainAllKeyFields() {
        String repr = user.toString();
        assertAll("toString output",
                () -> assertTrue(repr.contains("username='alice'")),
                () -> assertTrue(repr.contains("password='secret'")),
                () -> assertTrue(repr.contains("userType=STUDENT")),
                () -> assertTrue(repr.contains("id=1"))
        );
    }
}
