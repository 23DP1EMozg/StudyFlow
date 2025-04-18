package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.*;
import com.example.eksamens_vm.utils.TypeConvertionManager;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.stream.Collectors;

public class TestService {
    private Session session = Session.getInstance();
    private GroupService groupService = new GroupService();
    private JsonService jsonService = new JsonService();
    private RoomService roomService = new RoomService();
    private TypeConvertionManager typeConvertionManager = new TypeConvertionManager();
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

    public List<Test> getAllUserCreatedTests(int userId, int roomId){
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        return tests.stream().filter(t -> t.getTeacherId() == userId && t.getRoomId() == roomId).collect(Collectors.toList());
    }

    public List<Test> getAllUserAssignedTests(int userId, int roomId) throws RoomNotFoundException, UserNotInGroupException, GroupNotFoundException {
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        Group group = groupService.getUsersGroupInRoom(userId, roomId);

        return tests.stream().filter(t -> t.getGroupId() == group.getId()).collect(Collectors.toList());



    }

    public Test getTestById(int testId) throws TestNotFoundException {
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        return tests
                .stream()
                .filter(t -> t.getId() == testId)
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("test not found"));
    }

    public boolean testExists(int testId) throws TestNotFoundException {
        List<Test> tests = jsonService.getAll("tests.json", Test.class);
        return tests.stream().anyMatch(t -> t.getId() == testId);
    }

    public Test getRoomTestByName(String name, int roomId) throws TestNotFoundException {
        List<Test> tests = jsonService.getAll("tests.json", Test.class);

        return tests
                .stream()
                .filter(t -> t.getName().equals(name) && t.getRoomId() == roomId)
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("test not found"));
    }

    public int getGradeFromPercentage(double percentage){
        int grade = 0;
        if(0 <= percentage && percentage <= 14.4){
            grade = 1;
        }else if(14.5 <= percentage && percentage <= 24.4){
            grade = 2;
        }else if(24.5 <= percentage && percentage <= 34.4){
            grade = 3;
        }else if(34.5 <= percentage && percentage <= 44.4){
            grade = 4;
        }else if(44.5 <= percentage && percentage <= 54.4){
            grade = 5;
        }else if(54.5 <= percentage && percentage <= 64.4){
            grade = 6;
        }else if(64.5 <= percentage && percentage <= 74.4){
            grade = 7;
        }else if(74.5 <= percentage && percentage <= 84.4){
            grade = 8;
        }else if(84.5 <= percentage && percentage <= 94.4){
            grade = 9;
        }else if(percentage >= 94.5){
            grade = 10;
        }
        return grade;
    }

    private boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void saveTestAttempt(int testId, int userId, String percentage) throws UserNotFoundException, RoomNotFoundException, TestNotFoundException, GroupNotFoundException {
        Test test = getTestById(testId);
        if(!isNumeric(percentage) || percentage.isBlank()){
            throw new InputMismatchException("invalid percentage");
        }

        TestAttempt testAttempt = new TestAttempt(
                userId,
                getGradeFromPercentage(Double.parseDouble(percentage)),
                testId,
                Double.parseDouble(percentage)
        );


        jsonService.save(testAttempt, "test_attempts.json", TestAttempt.class);

        if(isTestCompleted(testId)){
            test.setTestStatus(TestStatus.COMPLETE);
            List<Test> tests = jsonService.getAll("tests.json", Test.class);

            for(int i=0; i<tests.size(); i++){
                if(tests.get(i).getId() == test.getId()){
                    tests.set(i, test);
                    jsonService.saveMany(tests, "tests.json");
                }
            }
        }
    }

    public List<TestAttempt> mergeTestAttemptsWithUsers(int testId, List<User> users) {
        List<TestAttempt> mergedAttempts = new ArrayList<>();
        List<TestAttempt> testAttempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList();

        for(int i = 0; i< users.size(); i++){
            User user = users.get(i);
            Optional<TestAttempt> match = testAttempts
                    .stream()
                    .filter(t -> t.getUserId() == user.getId())
                    .findFirst();

            if(match.isPresent()){
                mergedAttempts.add(match.get());
            }else{
                mergedAttempts.add(typeConvertionManager.convertUserToTestAttempt(user, testId));
            }

        }
        return mergedAttempts;
    }

    public boolean isTestCompleted(int testId) throws TestNotFoundException, UserNotFoundException, RoomNotFoundException, GroupNotFoundException {
        Test test = getTestById(testId);
        List<User> users = groupService.getAllUsersInGroup(test.getGroupId());
        List<TestAttempt> testAttempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList();

        return users.size() == testAttempts.size();

    }

    public ObservableList<TestAttemptTable> sortTestAttempts(List<TestAttempt> testAttempts) throws UserNotFoundException {
        List<TestAttempt> attempts = testAttempts
                                .stream()
                                .sorted(Comparator.comparing(TestAttempt::getPercent).reversed())
                                .toList();

        return typeConvertionManager.convertToTestAttemptTable(attempts);
    }

    public double getAverageGrade(int testId) {
        List<TestAttempt> attempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList();

        double sum = 0;
        for(int i = 0; i< attempts.size(); i++){
            sum += attempts.get(i).getGrade();
        }

        return sum/attempts.size();
    }


}
