package services_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.Room;
import com.example.eksamens_vm.models.User;
import com.example.eksamens_vm.services.JsonService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import com.example.eksamens_vm.data.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {

    private RoomService roomService;
    private User loggedInUser;
    private List<Room> mockRooms = new ArrayList<>();
    private List<User> mockUsers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        loggedInUser = new User(1, "testUser", "pass", UserRole.STUDENT, new ArrayList<>());

        // Set session user
        Session.getInstance().setLoggedInUser(loggedInUser);

        roomService = new RoomService() {
            {
                this.jsonService = new JsonService() {
                    @Override
                    public <T> void save(T object, String filename, Class<T> type) {
                        if (object instanceof Room room) {
                            mockRooms.add(room);
                        }
                    }

                    @Override
                    public <T> List<T> getAll(String filename, Class<T> type) {
                        if (filename.equals("rooms.json")) return (List<T>) mockRooms;
                        if (filename.equals("users.json")) return (List<T>) mockUsers;
                        return new ArrayList<>();
                    }

                    @Override
                    public <T> void saveMany(List<T> list, String filename) {
                        if (filename.equals("rooms.json")) {
                            mockRooms = (List<Room>) list;
                        }
                        if (filename.equals("users.json")) {
                            mockUsers = (List<User>) list;
                        }
                    }
                };

                this.userService = new UserService() {
                    @Override
                    public User getUserById(int id) throws UserNotFoundException {
                        return mockUsers.stream()
                                .filter(u -> u.getId() == id)
                                .findFirst()
                                .orElseThrow(() -> new UserNotFoundException("User not found"));
                    }

                    @Override
                    public boolean userExists(int id) {
                        return mockUsers.stream().anyMatch(u -> u.getId() == id);
                    }
                };
            }
        };

        mockUsers.clear();
        mockRooms.clear();
        mockUsers.add(loggedInUser);
    }

    @Test
    void testCreateRoom_success() throws InputFieldEmptyException {
        roomService.createRoom("Test Room");

        assertEquals(1, mockRooms.size());
        assertEquals("Test Room", mockRooms.get(0).getName());
    }

    @Test
    void testCreateRoom_failsOnEmptyName() {
        assertThrows(InputFieldEmptyException.class, () -> {
            roomService.createRoom("");
        });
    }

    @Test
    void testRequestRoom_success() throws Exception {
        Room room = new Room(
                new ArrayList<>(), 1, "Room 1", 1,
                new ArrayList<>(), 12345678
        );
        mockRooms.add(room);

        User requester = new User(2, "joiner", "pass", UserRole.STUDENT, new ArrayList<>());
        mockUsers.add(requester);

        Room result = roomService.requestRoom(12345678, requester);

        assertTrue(result.getJoinRequests().contains(2));
    }

    @Test
    void testRequestRoom_alreadyRequested() throws Exception {
        Room room = new Room(
                new ArrayList<>(), 1, "Room 1", 1,
                new ArrayList<>(List.of(2)), 12345678
        );
        mockRooms.add(room);

        User requester = new User(2, "joiner", "pass", UserRole.STUDENT, new ArrayList<>());
        mockUsers.add(requester);

        assertThrows(RoomAlreadyRequestedException.class, () -> {
            roomService.requestRoom(12345678, requester);
        });
    }

    @Test
    void testRequestRoom_alreadyInRoom() throws Exception {
        Room room = new Room(
                new ArrayList<>(List.of(new com.example.eksamens_vm.models.RoomUser(2, 0))),
                1, "Room 1", 1, new ArrayList<>(), 12345678
        );
        mockRooms.add(room);

        User requester = new User(2, "joiner", "pass", UserRole.STUDENT, List.of(1));
        mockUsers.add(requester);

        assertThrows(UserAlreadyInRoomException.class, () -> {
            roomService.requestRoom(12345678, requester);
        });
    }

    @Test
    void testAcceptUserRequest_success() throws Exception {
        User user = new User(2, "newUser", "pass", UserRole.STUDENT, new ArrayList<>());
        mockUsers.add(user);

        Room room = new Room(
                new ArrayList<>(), 1, "Room A", 1,
                new ArrayList<>(List.of(2)), 87654321
        );
        mockRooms.add(room);

        roomService.acceptUserRequest(2, 1);

        assertTrue(mockRooms.get(0).getUsers().stream().anyMatch(u -> u.getUser() == 2));
    }

    @Test
    void testRejectUserRequest_removesRequest() throws Exception {
        User user = new User(2, "rejectUser", "pass", UserRole.STUDENT, new ArrayList<>());
        mockUsers.add(user);

        Room room = new Room(new ArrayList<>(), 1, "Room A", 1,
                new ArrayList<>(List.of(2)), 12345678);
        mockRooms.add(room);

        roomService.rejectUserRequest(2, 1);

        assertFalse(mockRooms.get(0).getJoinRequests().contains(2));
    }

    @Test
    void testRemoveUser_success() throws Exception {
        User user = new User(2, "leaveUser", "pass", UserRole.STUDENT, new ArrayList<>(List.of(1)));
        mockUsers.add(user);

        Room room = new Room(
                new ArrayList<>(List.of(new com.example.eksamens_vm.models.RoomUser(2, 0))),
                1, "Room X", 1,
                new ArrayList<>(), 12345678
        );
        mockRooms.add(room);

        roomService.removeUser(2, 1);

        assertFalse(mockRooms.get(0).getUsers().stream().anyMatch(u -> u.getUser() == 2));
        assertFalse(user.getRooms().contains(1));
    }
}
