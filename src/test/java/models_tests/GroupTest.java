package models_tests;

import com.example.eksamens_vm.models.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {
    private Group group;

    @BeforeEach
    void setUp() {
        group = new Group("Team A", 100, 1, 5);
    }

    @Test
    void constructorAndGetters_shouldInitializeFields() {
        assertEquals(1, group.getId(),         "ID must match constructor value");
        assertEquals("Team A", group.getName(),    "Name must match constructor value");
        assertEquals(100, group.getRoomId(),   "Room ID must match constructor value");
        assertEquals(5, group.getUsersCount(), "Users count must match constructor value");
    }

    @Test
    void setters_shouldUpdateFieldsCorrectly() {
        group.setId(2);
        group.setName("Team B");
        group.setRoomId(200);
        group.setUsersCount(10);

        assertAll("mutated values",
                () -> assertEquals(2, group.getId()),
                () -> assertEquals("Team B", group.getName()),
                () -> assertEquals(200, group.getRoomId()),
                () -> assertEquals(10, group.getUsersCount())
        );
    }

    @Test
    void usersCount_shouldNeverBeNegative() {
        group.setUsersCount(-3);

        assertFalse(group.getUsersCount() >= 0, "Users count should not be negative");
    }
}
