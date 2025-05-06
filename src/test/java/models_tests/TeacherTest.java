package models_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher(
                15,
                "profX",
                "xpass",
                UserRole.TEACHER,
                List.of(10, 20, 30)
        );
    }

    @Test
    void constructorAndGetters() {
        assertEquals(15, teacher.getId());
        assertEquals("profX", teacher.getUsername());
        assertEquals("xpass", teacher.getPassword());
        assertEquals(UserRole.TEACHER, teacher.getUserType());
        assertEquals(List.of(10, 20, 30), teacher.getRooms());
    }

    @Test
    void settersUpdateInheritedFields() {
        teacher.setId(16);
        teacher.setUsername("profY");
        teacher.setPassword("ypass");
        teacher.setUserType(UserRole.STUDENT);
        teacher.setRooms(List.of(40));

        assertEquals(16, teacher.getId());
        assertEquals("profY", teacher.getUsername());
        assertEquals("ypass", teacher.getPassword());
        assertEquals(UserRole.STUDENT, teacher.getUserType());
        assertEquals(List.of(40), teacher.getRooms());
    }

    @Test
    void toStringIncludesKeyFields() {
        String repr = teacher.toString();
        assertTrue(repr.contains("username='profX'"));
        assertTrue(repr.contains("password='xpass'"));
        assertTrue(repr.contains("userType=TEACHER"));
        assertTrue(repr.contains("id=15"));
    }

    @Test
    void independentInstancesMaintainState() {
        Teacher other = new Teacher(99, "other", "opass", UserRole.TEACHER, List.of());
        teacher.setUsername("changed");
        assertEquals("changed", teacher.getUsername());
        assertEquals("other", other.getUsername());
    }
}
