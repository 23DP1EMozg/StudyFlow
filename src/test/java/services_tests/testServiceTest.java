package services_tests;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.*;

import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.TestService;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {

    private TestService testService;
    private Session session;
    private JsonService jsonService;

    @BeforeEach
    void setUp() throws Exception {
        clearTestData();

        session = Session.getInstance();
        session.setLoggedInUser(new User(1, "Teacher", "Teacher", UserRole.STUDENT, List.of()));

        List<RoomUser> users = new ArrayList<>();
        List<Integer> joinRequests = new ArrayList<>();
        Room room = new Room(users, 1, "Test Room", 1, joinRequests, 1234);
        session.setJoinedRoom(room);

        jsonService = new JsonService();
        testService = new TestService();

        // Save group
        Group group = new Group("Group A", 1, 1, 0);
        jsonService.save(group, "groups.json", Group.class);
    }

    @AfterEach
    void tearDown() {
        clearTestData();
    }

    private void clearTestData() {
        new File("tests.json").delete();
        new File("groups.json").delete();
        new File("test_attempts.json").delete();
    }



    @Test
    void testCreateTestWithEmptyFields() {
        assertThrows(InputFieldEmptyException.class, () -> {
            testService.createTest("", "Group A");
        });

        assertThrows(InputFieldEmptyException.class, () -> {
            testService.createTest("Algebra", "");
        });
    }



    @Test
    void testGetGradeFromPercentage() {
        assertEquals(1, testService.getGradeFromPercentage(10));
        assertEquals(5, testService.getGradeFromPercentage(50));
        assertEquals(10, testService.getGradeFromPercentage(95));
    }

    @Test
    void testGetTestByIdThrowsIfNotFound() {
        assertThrows(TestNotFoundException.class, () -> {
            testService.getTestById(99);
        });
    }


}
