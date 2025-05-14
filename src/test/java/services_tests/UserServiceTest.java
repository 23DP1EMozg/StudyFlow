package services_tests;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.utils.SceneManager;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private JsonService jsonService;
    private Session session;
    private SceneManager sceneManager;
    private List<User> mockUserList;

    // This will simulate the JsonService behavior
    public class FakeJsonService extends JsonService {
        @Override
        public <T> List<T> getAll(String fileName, Class<T> clazz) {
            // Return a mock list of users
            if (fileName.equals("users.json") && clazz == User.class) {
                return (List<T>) mockUserList;
            }
            return new ArrayList<>();
        }
    }

    // This will simulate the SceneManager behavior
    public class FakeSceneManager extends SceneManager {
        public static void switchScenes(ActionEvent event, String fxmlFile, String title) {
            // Simulate scene switching
        }

        public static void clearHistory() {
            // Simulate clearing history
        }
    }

    @BeforeEach
    public void setUp() {
        // Set up the mock data
        mockUserList = new ArrayList<>();

        // Create some mock users with UserRole and List<Integer> for rooms
        List<Integer> rooms1 = new ArrayList<>();
        rooms1.add(101);
        mockUserList.add(new User(1, "john_doe", "password", UserRole.TEACHER, rooms1));

        List<Integer> rooms2 = new ArrayList<>();
        rooms2.add(102);
        mockUserList.add(new User(2, "jane_doe", "password123", UserRole.STUDENT, rooms2));

        // Initialize the services
        jsonService = new FakeJsonService();
        sceneManager = new FakeSceneManager();

        // Use the Singleton pattern to get the session instance
        session = Session.getInstance();

        // Initialize the UserService with the fake dependencies
        userService = new UserService();
    }



    // Test userExists(int userId)
    @Test
    public void testUserExistsWithUserId() {
        boolean exists = userService.userExists(1);

        assertTrue(exists);
    }


    // Test getUserById throws UserNotFoundException
    @Test
    public void testGetUserByIdThrowsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99));
    }



    // Test logout()
    @Test
    public void testLogout() {
        ActionEvent event = new ActionEvent();

        // Setting logged-in user before logout
        session.setLoggedInUser(new User(1, "john_doe", "password", UserRole.TEACHER, new ArrayList<>()));

        // Call logout method
        userService.logout(event);

        // Verify that session is cleared
        assertNull(session.getLoggedInUser());
        assertNull(session.getJoinedRoom());
    }
}
