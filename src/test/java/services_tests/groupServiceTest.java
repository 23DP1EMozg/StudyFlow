package services_tests;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GroupServiceTest {

    private GroupService groupService;
    private InMemoryJsonService jsonService;
    private Session testSession;

    static class InMemoryJsonService extends JsonService {
        List<Group> groups = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();

        @Override
        public <T> LinkedList<T> getAll(String filename, Class<T> clazz) {
            LinkedList<T> result = new LinkedList<>();
            if (clazz == Group.class && "groups.json".equals(filename)) {
                for (Group g : groups) result.add(clazz.cast(g));
            } else if (clazz == Room.class && "rooms.json".equals(filename)) {
                for (Room r : rooms) result.add(clazz.cast(r));
            }
            return result;
        }

        @Override
        public <T> void save(T obj, String filename, Class<T> clazz) {
            if (clazz == Group.class && "groups.json".equals(filename)) {
                groups.add((Group) obj);
            }
        }

        @Override
        public <T> void saveMany(List<T> list, String filename) {
            if ("groups.json".equals(filename) && !list.isEmpty() && list.get(0) instanceof Group) {
                groups = new ArrayList<>((Collection<? extends Group>) list);
            } else if ("rooms.json".equals(filename) && !list.isEmpty() && list.get(0) instanceof Room) {
                rooms = new ArrayList<>((Collection<? extends Room>) list);
            }
        }
    }

    static class FakeRoomService extends RoomService {
        private final List<Room> fakeRooms;

        FakeRoomService(List<Room> rooms) {
            this.fakeRooms = rooms;
        }

        @Override
        public Room getRoomById(int id) throws RoomNotFoundException {
            return fakeRooms.stream()
                    .filter(r -> r.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new RoomNotFoundException("Room " + id + " not found"));
        }
    }

    @BeforeEach
    void setUp() {
        jsonService = new InMemoryJsonService();
        jsonService.groups.clear();
        jsonService.rooms.clear();

        Room room1 = new Room(
                new ArrayList<>(Arrays.asList(new RoomUser(10, 1), new RoomUser(20, 0))),
                1, "R1", 1, new ArrayList<>(), 1111
        );
        jsonService.rooms.add(room1);

        jsonService.groups.add(new Group("G1", 1, 1, 1));
        jsonService.groups.add(new Group("G2", 1, 2, 0));

        groupService = new GroupService();
        groupService.jsonService = jsonService;
        groupService.roomService = new FakeRoomService(jsonService.rooms);

        testSession = Session.getInstance();
        testSession.setJoinedRoom(room1);
        groupService.session = testSession;

        groupService.userService = new UserService() {
            @Override
            public User getUserById(int id) {
                return new User(id, "u" + id, "pw",
                        id == 10 ? UserRole.STUDENT : UserRole.TEACHER,
                        List.of(1));
            }
        };
    }

    @Test
    void createGroup_successAndDuplicateThrows() throws Exception {
        groupService.createGroup("G3", 1);
        assertTrue(jsonService.groups.stream()
                .anyMatch(g -> g.getName().equals("G3") && g.getRoomId() == 1));

        assertThrows(GroupExistsException.class,
                () -> groupService.createGroup("G1", 1));
    }

    @Test
    void getAllRoomGroupNames_and_getRoomGroupByName() throws Exception {
        List<String> names = groupService.getAllRoomGroupNames(1);
        assertEquals(2, names.size());
        assertTrue(names.contains("G1"));
        assertTrue(names.contains("G2"));

        Group g2 = groupService.getRoomGroupByName("g2", 1);
        assertEquals(2, g2.getId());

        assertThrows(GroupNotFoundException.class,
                () -> groupService.getRoomGroupByName("nope", 1));
    }

    @Test
    void addUserToGroup_movesUserAndUpdatesCounts() throws Exception {
        groupService.addUserToGroup(2, 20);
        assertEquals(1,
                jsonService.groups.stream().filter(g -> g.getId() == 2)
                        .findFirst().get().getUsersCount());
        assertTrue(jsonService.rooms.get(0).getUsers().stream()
                .anyMatch(ru -> ru.getUser() == 20 && ru.getGroup() == 2));

        groupService.addUserToGroup(2, 10);
        assertEquals(0,
                jsonService.groups.stream().filter(g -> g.getId() == 1)
                        .findFirst().get().getUsersCount());
        assertEquals(2,
                jsonService.groups.stream().filter(g -> g.getId() == 2)
                        .findFirst().get().getUsersCount());
    }

    @Test
    void getUsersGroupinRoom_stringAnd_object() throws Exception {
        assertEquals("G1", groupService.getUsersGroupinRoom(10, 1));
        assertEquals(1, groupService.getUsersGroupInRoom(10, 1).getId());

        jsonService.rooms.get(0).getUsers().stream()
                .filter(ru -> ru.getUser() == 20).findFirst()
                .ifPresent(ru -> ru.setGroup(0));

        assertEquals("Not in a group",
                groupService.getUsersGroupinRoom(20, 1));
        assertThrows(UserNotInGroupException.class,
                () -> groupService.getUsersGroupInRoom(20, 1));
    }
}
