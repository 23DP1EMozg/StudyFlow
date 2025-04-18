package com.example.eksamens_vm.utils;

import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.RoomService;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TypeConvertionManager {
    private GroupService groupService = new GroupService();
    private UserService userService = new UserService();

    public ObservableList<UserTable> convertToUserTable(List<User> users, int roomId) throws UserNotFoundException, RoomNotFoundException, GroupNotFoundException {
        ObservableList<UserTable> userTables = FXCollections.observableArrayList();

        for(int i = 0; i<users.size(); i++){
            String group = groupService.getUsersGroupinRoom(users.get(i).getId(), roomId);
            userTables.add(new UserTable(group, users.get(i).getUserType(), users.get(i).getUsername()));
        }
        return userTables;
    }

    public ObservableList<GroupTable> convertToGroupTable(List<Group> groups) throws GroupNotFoundException, RoomNotFoundException {
        ObservableList<GroupTable> groupTables = FXCollections.observableArrayList();

        for(int i = 0; i<groups.size(); i++){
            Group group = groups.get(i);
            groupTables.add(new GroupTable(group.getName(), String.valueOf(group.getUsersCount())));
        }
        return groupTables;
    }

    public ObservableList<TestTable> convertToTestTable(List<Test> tests) throws GroupNotFoundException, UserNotFoundException {
        ObservableList<TestTable> testTables = FXCollections.observableArrayList();

        for(int i = 0; i<tests.size(); i++){
            Test test = tests.get(i);
            Group group = groupService.getGroupById(test.getGroupId());
            User teacher = userService.getUserById(test.getTeacherId());
            TestTable testTable = new TestTable(
                test.getName(),
                test.getTestStatus(),
                teacher.getUsername(),
                group.getName()
            );

            testTables.add(testTable);
        }

        return testTables;
    }

    public ObservableList<TestAttemptTable> convertToTestAttemptTable(List<TestAttempt> testAttempts) throws UserNotFoundException {
        ObservableList<TestAttemptTable> testAttemptTables = FXCollections.observableArrayList();

        for(int i = 0; i<testAttempts.size(); i++){
            TestAttempt testAttempt = testAttempts.get(i);
            User user = userService.getUserById(testAttempt.getUserId());

            TestAttemptTable testAttemptTable = new TestAttemptTable(
                    user.getUsername(),
                    testAttempt.getGrade(),
                    testAttempt.getPercent()
            );
            testAttemptTables.add(testAttemptTable);
        }
        return testAttemptTables;
    }

    public ObservableList<TestAttemptTable> convertUsersToTestAttemptTable(List<User> users) throws UserNotFoundException {
        ObservableList<TestAttemptTable> testAttemptTables = FXCollections.observableArrayList();

        for(int i = 0; i<users.size(); i++){
            User user = users.get(i);

            TestAttemptTable testAttemptTable = new TestAttemptTable(
                    user.getUsername(),
                    -1,
                    -1
            );

            testAttemptTables.add(testAttemptTable);
        }

        return testAttemptTables;
    }

    public TestAttempt convertUserToTestAttempt(User user, int testId){
        return new TestAttempt(
                user.getId(),
                -1,
                testId,
                -1
        );
    }

}

