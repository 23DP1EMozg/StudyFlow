package models_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.models.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(
                7,
                "jdoe",
                "pass123",
                UserRole.STUDENT,
                "GroupX",
                List.of(100, 200)
        );
    }

    @Test
    void constructorAndInheritedGetters() {
        assertEquals(7, student.getId());
        assertEquals("jdoe", student.getUsername());
        assertEquals("pass123", student.getPassword());
        assertEquals(UserRole.STUDENT, student.getUserType());
        assertEquals("GroupX", student.getGroup());
        assertEquals(List.of(100, 200), student.getRooms());
    }

    @Test
    void settersUpdateOwnAndInheritedFields() {
        student.setId(8);
        student.setUsername("asmith");
        student.setPassword("newpass");
        student.setUserType(UserRole.TEACHER);
        student.setGroup("GroupY");
        student.setRooms(List.of(300));

        assertEquals(8, student.getId());
        assertEquals("asmith", student.getUsername());
        assertEquals("newpass", student.getPassword());
        assertEquals(UserRole.TEACHER, student.getUserType());
        assertEquals("GroupY", student.getGroup());
        assertEquals(List.of(300), student.getRooms());
    }

    @Test
    void independentInstancesMaintainState() {
        Student other = new Student(9, "bwayne", "darkknight", UserRole.STUDENT, "GroupZ", List.of());
        student.setGroup("ChangedGroup");
        assertEquals("ChangedGroup", student.getGroup());
        assertEquals("GroupZ", other.getGroup());
    }
}
