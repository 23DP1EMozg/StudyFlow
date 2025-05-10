package services_tests;

import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.services.JsonService;
import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JsonServiceTest {

    private JsonService jsonService;
    private static final String TEST_DIR = "src/test/resources/test_data";
    private static final String TEST_FILE = "users_test.json";

    public static class TestUser {
        public int id;
        public String username;
        public String password;
        public UserRole role;
        public List<Integer> rooms;

        public TestUser(int id, String username, String password, UserRole role, List<Integer> rooms) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.rooms = rooms;
        }

        // equals and hashCode for assertions
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestUser)) return false;
            TestUser user = (TestUser) o;
            return id == user.id &&
                    Objects.equals(username, user.username) &&
                    Objects.equals(password, user.password) &&
                    role == user.role &&
                    Objects.equals(rooms, user.rooms);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, username, password, role, rooms);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        jsonService = new JsonService();
        // Ensure the directory exists
        Files.createDirectories(Paths.get(TEST_DIR));
        // Clean up the test file before each test
        Files.deleteIfExists(Paths.get(TEST_DIR, TEST_FILE));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up the test file after each test
        Files.deleteIfExists(Paths.get(TEST_DIR, TEST_FILE));
    }

//    @Test
//    void saveAndGetAll_shouldPersistAndRetrieveData() {
//        TestUser user = new TestUser(1, "test", "pw", UserRole.STUDENT, List.of(1, 2));
//        jsonService.save(user, TEST_FILE, TestUser.class);
//
//        List<TestUser> users = jsonService.getAll(TEST_FILE, TestUser.class);
//        assertEquals(1, users.size());
//        assertEquals(user, users.get(0));
//    }

//    @Test
//    void saveMany_shouldOverwriteAndRetrieveList() {
//        List<TestUser> users = List.of(
//                new TestUser(1, "user1", "pw1", UserRole.STUDENT, List.of(1)),
//                new TestUser(2, "user2", "pw2", UserRole.TEACHER, List.of(2))
//        );
//        jsonService.saveMany(users, TEST_FILE);
//
//        List<TestUser> loaded = jsonService.getAll(TEST_FILE, TestUser.class);
//        assertEquals(2, loaded.size());
//        assertEquals(users, loaded);
//    }

    @Test
    void getAll_onNonexistentFile_shouldReturnEmptyList() throws Exception {
        // Test when the file doesn't exist
        Path nonexistentPath = Paths.get(TEST_DIR, "nonexistent.json");
        Files.deleteIfExists(nonexistentPath);  // Ensure the file doesn't exist

        List<TestUser> result = jsonService.getAll("nonexistent.json", TestUser.class);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

//    @Test
//    void getAll_onEmptyFile_shouldReturnEmptyList() throws Exception {
//        // Test when the file exists but is empty
//        Path path = Paths.get(TEST_DIR, TEST_FILE);
//        Files.createFile(path);  // Create an empty file
//
//        List<TestUser> result = jsonService.getAll(TEST_FILE, TestUser.class);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//    }
}
