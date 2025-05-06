package models_tests;

import com.example.eksamens_vm.models.RoomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomUserTest {

    private RoomUser roomUser;

    @BeforeEach
    void setUp() {
        roomUser = new RoomUser(10, 20);
    }

    @Test
    void constructorAndGetters() {
        assertEquals(10, roomUser.getUser());
        assertEquals(20, roomUser.getGroup());
    }

    @Test
    void settersUpdateFields() {
        roomUser.setUser(30);
        roomUser.setGroup(40);
        assertEquals(30, roomUser.getUser());
        assertEquals(40, roomUser.getGroup());
    }

    @Test
    void multipleInstancesMaintainIndependentState() {
        RoomUser other = new RoomUser(50, 60);
        roomUser.setUser(70);
        roomUser.setGroup(80);
        assertEquals(70, roomUser.getUser());
        assertEquals(80, roomUser.getGroup());
        assertEquals(50, other.getUser());
        assertEquals(60, other.getGroup());
    }

    @Test
    void toStringReflectsFields() {
        String repr = roomUser.toString();
        assertTrue(repr.contains("user=10"));
        assertTrue(repr.contains("group=20"));
    }
}
