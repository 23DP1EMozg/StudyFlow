package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotInGroupException;
import com.example.eksamens_vm.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class TestService {
    private Session session = Session.getInstance();
    private GroupService groupService = new GroupService();
    private JsonService jsonService = new JsonService();
    private RoomService roomService = new RoomService();
    private int generateNewId(){
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        return tests.isEmpty() ? 1 : tests.getLast().getId() + 1;
    }


    public void createTest(String name, String groupName) throws GroupNotFoundException, InputFieldEmptyException {

        if(name.isBlank() || groupName == null || groupName.isBlank()){
            throw new InputFieldEmptyException("cannot leave fields blank!");
        }

        Group group = groupService.getRoomGroupByName(groupName, session.getJoinedRoom().getId());

        Test test = new Test(
                generateNewId(),
                session.getLoggedInUser().getId(),
                session.getJoinedRoom().getId(),
                group.getId(),
                name,
                TestStatus.INCOMPLETE
        );

        jsonService.save(test, "tests.json", Test.class);
    }

    public List<Test> getAllUserCreatedTests(int userId){
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        return tests.stream().filter(t -> t.getTeacherId() == userId).collect(Collectors.toList());
    }

    public List<Test> getAllUserAssignedTests(int userId, int roomId) throws RoomNotFoundException, UserNotInGroupException, GroupNotFoundException {
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        Group group = groupService.getUsersGroupInRoom(userId, roomId);

        return tests.stream().filter(t -> t.getGroupId() == group.getId()).collect(Collectors.toList());



    }


}
