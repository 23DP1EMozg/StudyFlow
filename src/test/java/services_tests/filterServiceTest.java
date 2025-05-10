package services_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.services.FilterService;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FilterServiceTest {

    private FilterService filterService;

    @BeforeEach
    void setUp() {
        filterService = new FilterService();

        // Fake RoomService
        filterService.roomService = new RoomService() {
            @Override
            public Room getRoomById(int id) throws RoomNotFoundException {
                if (id == 1) {
                    return new Room(
                            List.of(
                                    new RoomUser(4, 1),
                                    new RoomUser(3, 1),
                                    new RoomUser(2, 2)
                            ),
                            1,
                            "room",
                            1,
                            List.of(),
                            13793055
                    );
                } else if (id == 2) {
                    return new Room(
                            List.of(
                                    new RoomUser(1, 0),
                                    new RoomUser(2, 0),
                                    new RoomUser(3, 0),
                                    new RoomUser(4, 0)
                            ),
                            2,
                            "room2",
                            1,
                            List.of(),
                            40102917
                    );
                }
                throw new RoomNotFoundException("Room " + id + " not found");
            }
        };

        // Fake GroupService
        filterService.groupService = new GroupService() {
            @Override
            public Group getRoomGroupByName(String name, int roomId) throws GroupNotFoundException {
                if ("DP1-1".equals(name) && roomId == 1) {
                    return new Group("DP1-1", 1, 1, 5);
                }
                throw new GroupNotFoundException("Group " + name + " not found in room " + roomId);
            }
        };

        // Fake UserService
        filterService.userService = new UserService() {
            @Override
            public User getUserById(int id) throws UserNotFoundException {
                switch (id) {
                    case 1: return new User(1, "alice",   "pw", UserRole.TEACHER, List.of(2));
                    case 2: return new User(2, "bob",     "pw", UserRole.TEACHER, List.of(2));
                    case 3: return new User(3, "charlie","pw", UserRole.STUDENT, List.of(1,2));
                    case 4: return new User(4, "delta",   "pw", UserRole.STUDENT, List.of(1));
                    default: throw new UserNotFoundException("User " + id + " not found");
                }
            }
        };
    }

    @Test
    void testGetAllUsersInGroup() throws Exception {
        // DP1-1, room 1 has users 4,3 in group 1
        List<User> users = filterService.getAllUsersInGroup("DP1-1", 1);
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getId() == 4 && u.getUsername().equals("delta")));
        assertTrue(users.stream().anyMatch(u -> u.getId() == 3 && u.getUsername().equals("charlie")));
    }

    @Test
    void testGetAllTeachersInRoom() throws Exception {
        // room 2 has users 1,2,3,4; teachers are 1,2
        List<User> teachers = filterService.getAllTeachersInRoom(2);
        assertEquals(2, teachers.size());
        assertTrue(teachers.stream().allMatch(u -> u.getUserType() == UserRole.TEACHER));
        assertTrue(teachers.stream().anyMatch(u -> u.getUsername().equals("alice")));
        assertTrue(teachers.stream().anyMatch(u -> u.getUsername().equals("bob")));
    }

    @Test
    void testGetAllTeachersInRoomNames() throws Exception {
        List<String> names = filterService.getAllTeachersInRoomNames(2);
        assertEquals(List.of("alice", "bob"), names);
    }

    @Test
    void testGetAllStudentsInRoom() throws Exception {
        // room 2: charlie,id3 & delta,id4 are students
        List<User> students = filterService.getAllStudentsInRoom(2);
        assertEquals(2, students.size());
        assertTrue(students.stream().allMatch(u -> u.getUserType() == UserRole.STUDENT));
        assertTrue(students.stream().anyMatch(u -> u.getUsername().equals("charlie")));
        assertTrue(students.stream().anyMatch(u -> u.getUsername().equals("delta")));
    }
}
