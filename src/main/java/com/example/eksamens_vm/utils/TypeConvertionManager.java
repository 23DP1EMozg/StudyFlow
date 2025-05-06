package com.example.eksamens_vm.utils;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.GroupNotFoundException;
import com.example.eksamens_vm.exceptions.RoomNotFoundException;
import com.example.eksamens_vm.exceptions.TestNotFoundException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.services.GroupService;
import com.example.eksamens_vm.services.TestService;
import com.example.eksamens_vm.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;

public class TypeConvertionManager {
    private GroupService groupService = new GroupService();
    private UserService userService = new UserService();
    private TestService testService = new TestService();
    private Session session = Session.getInstance();

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

    public ObservableList<TestTable> convertToTestTable(List<TestModel> testModels) throws GroupNotFoundException, UserNotFoundException {
        ObservableList<TestTable> testTables = FXCollections.observableArrayList();

        for(int i = 0; i< testModels.size(); i++){
            TestModel testModel = testModels.get(i);
            Group group = groupService.getGroupById(testModel.getGroupId());
            User teacher = userService.getUserById(testModel.getTeacherId());
            TestTable testTable = new TestTable(
                testModel.getName(),
                testModel.getTestStatus(),
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




    public ObservableList<RecentTestTable> testAttemptsToRecentTestTable(List<TestAttempt> testAttempts) throws TestNotFoundException {
        ObservableList<RecentTestTable> recentTestTables = FXCollections.observableArrayList();

        for(int i = 0; i<testAttempts.size(); i++){
            TestAttempt testAttempt = testAttempts.get(i);
            TestModel testModel = testService.getTestById(testAttempt.getTestId());
            recentTestTables.add(new RecentTestTable(testModel.getName(), testAttempt.getGrade()));
        }
        return recentTestTables;
    }

    public ObservableList<TestAttemptTable> sortTestAttempts(List<TestAttempt> testAttempts) throws UserNotFoundException {
        List<TestAttempt> attempts = testAttempts
                .stream()
                .sorted(Comparator.comparing(TestAttempt::getPercent).reversed())
                .toList();

        return convertToTestAttemptTable(attempts);
    }

    public ObservableList<RecentTestTeacherTable> testsToRecentTestTeacherTable(List<TestModel> testModels) throws GroupNotFoundException {
        ObservableList<RecentTestTeacherTable> recentTestTeacherTables = FXCollections.observableArrayList();
        for(int i = 0; i< testModels.size(); i++){
            TestModel testModel = testModels.get(i);
            String groupName = groupService.getGroupById(testModel.getGroupId()).getName();
            recentTestTeacherTables.add(new RecentTestTeacherTable(testModel.getName(), groupName));
        }
        return recentTestTeacherTables;
    }

}

