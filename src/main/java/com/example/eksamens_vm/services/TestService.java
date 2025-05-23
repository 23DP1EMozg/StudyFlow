package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.exceptions.*;
import com.example.eksamens_vm.models.*;


import java.util.*;
import java.util.stream.Collectors;

public class TestService {
    private Session session = Session.getInstance();
    private GroupService groupService = new GroupService();
    private JsonService jsonService = new JsonService();
    private int generateNewId(){
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);
        return testModels.isEmpty() ? 1 : testModels.getLast().getId() + 1;
    }


    public void createTest(String name, String groupName) throws GroupNotFoundException, InputFieldEmptyException {

        if(name.isBlank() || groupName == null || groupName.isBlank()){
            throw new InputFieldEmptyException("cannot leave fields blank!");
        }

        Group group = groupService.getRoomGroupByName(groupName, session.getJoinedRoom().getId());

        TestModel testModel = new TestModel(
                generateNewId(),
                session.getLoggedInUser().getId(),
                session.getJoinedRoom().getId(),
                group.getId(),
                name,
                TestStatus.INCOMPLETE
        );

        jsonService.save(testModel, "tests.json", TestModel.class);
    }

    public List<TestModel> getAllUserCreatedTests(int userId, int roomId){
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);
        int incompleteTestCount = 0;
        for(int i = 0; i< testModels.size(); i++){
            try {
                if(!isTestCompleted(testModels.get(i).getId())){
                    testModels.get(i).setTestStatus(TestStatus.INCOMPLETE);
                    incompleteTestCount++;
                }
            } catch (TestNotFoundException e) {
                throw new RuntimeException(e);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            } catch (RoomNotFoundException e) {
                throw new RuntimeException(e);
            } catch (GroupNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if(incompleteTestCount > 0){
            jsonService.saveMany(testModels, "tests.json");
        }

        return testModels.stream().filter(t -> t.getTeacherId() == userId && t.getRoomId() == roomId).collect(Collectors.toList());
    }

    public List<TestModel> getAllUserAssignedTests(int userId, int roomId) throws RoomNotFoundException, UserNotInGroupException, GroupNotFoundException {
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);
        Group group = groupService.getUsersGroupInRoom(userId, roomId);

        return testModels.stream().filter(t -> t.getGroupId() == group.getId()).collect(Collectors.toList());



    }

    public List<TestAttempt> getAllUsersCompletedTests(int userId, int roomId){

        return jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getUserId() == userId && t.getRoomId() == roomId)
                .toList();

    }

    public TestModel getTestById(int testId) throws TestNotFoundException {
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);
        return testModels
                .stream()
                .filter(t -> t.getId() == testId)
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("test not found"));
    }

    public boolean testExists(int testId) throws TestNotFoundException {
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);
        return testModels.stream().anyMatch(t -> t.getId() == testId);
    }

    public TestModel getRoomTestByName(String name, int roomId) throws TestNotFoundException {
        List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);

        return testModels
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

        if(!isNumeric(percentage) || percentage.isBlank()){
            throw new InputMismatchException("invalid percentage");
        }

        if(Double.parseDouble(percentage) < 0 || Double.parseDouble(percentage) > 100){
            throw new InputMismatchException("invalid percentage");
        }


        TestAttempt testAttempt = new TestAttempt(
                userId,
                getGradeFromPercentage(Double.parseDouble(percentage)),
                testId,
                Double.parseDouble(percentage),
                session.getJoinedRoom().getId()
        );


        jsonService.save(testAttempt, "test_attempts.json", TestAttempt.class);

        checkAndUpdateTestStatus(testId);
    }

    public TestModel checkAndUpdateTestStatus(int testId) throws TestNotFoundException, UserNotFoundException, RoomNotFoundException, GroupNotFoundException {
        TestModel testModel = getTestById(testId);
        if(isTestCompleted(testId)){
            testModel.setTestStatus(TestStatus.COMPLETE);
            List<TestModel> testModels = jsonService.getAll("tests.json", TestModel.class);

            for(int i = 0; i< testModels.size(); i++){
                if(testModels.get(i).getId() == testModel.getId()){
                    testModels.set(i, testModel);
                    jsonService.saveMany(testModels, "tests.json");
                    return testModels.get(i);
                }
            }
        }
        return testModel;
    }

    public TestAttempt convertUserToTestAttempt(User user, int testId) {
        return new TestAttempt(
                user.getId(),
                -1,
                testId,
                -1,
                session.getJoinedRoom().getId()

        );
    }


    public List<TestAttempt> mergeTestAttemptsWithUsers(int testId, List<User> users) throws TestNotFoundException {
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
                mergedAttempts.add(convertUserToTestAttempt(user, testId));
            }

        }




        return mergedAttempts;
    }

    public void updateTestAttempt(int userId, int testId, String percentage) throws TestNotFoundException {
        List<TestAttempt> testAttempts = jsonService.getAll("test_attempts.json", TestAttempt.class);

        if(!isNumeric(percentage) || percentage.isBlank()){
            throw new InputMismatchException("invalid percentage");
        }

        if(Double.parseDouble(percentage) < 0 || Double.parseDouble(percentage) > 100){
            throw new InputMismatchException("invalid percentage");
        }

        for(int i = 0; i< testAttempts.size(); i++){
            TestAttempt testAttempt1 = testAttempts.get(i);
            if(testAttempt1.getTestId() == testId && testAttempt1.getUserId() == userId){
                testAttempts.set(i, new TestAttempt(userId, getGradeFromPercentage(Double.parseDouble(percentage)), testId, Double.parseDouble(percentage), session.getJoinedRoom().getId()));
                break;
            }
        }

        jsonService.saveMany(testAttempts, "test_attempts.json");
    }

    public boolean isTestCompleted(int testId) throws TestNotFoundException, UserNotFoundException, RoomNotFoundException, GroupNotFoundException {
        TestModel testModel = getTestById(testId);
        List<User> users = groupService.getAllUsersInGroup(testModel.getGroupId());
        List<TestAttempt> testAttempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList();

        return users.size() == testAttempts.size();

    }



    public List<TestAttempt> sortTestAttemptsAsList(List<TestAttempt> testAttempts){

        return testAttempts
                .stream()
                .sorted(Comparator.comparing(TestAttempt::getPercent).reversed())
                .toList();
    }

    public int getUsersPlacementInTest(int testId, int userId) throws UserNotFoundException {
        List<TestAttempt> attempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList();

        List<TestAttempt> sorted = sortTestAttemptsAsList(attempts);
        for(int i = 0; i<sorted.size(); i++){
            if(sorted.get(i).getUserId() == userId){
                return i + 1;
            }
        }

        throw new UserNotFoundException("user not found");

    }

    public int getUserCountInTest(int testId) {
        return jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getTestId() == testId)
                .toList()
                .size();
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

        double average = sum/attempts.size();
        return Math.round(average * 10) / 10.0;
    }

    public double getUsersAverageGrade(int userId, int roomId) {
        List<TestAttempt> attempts = jsonService.getAll("test_attempts.json", TestAttempt.class)
                .stream()
                .filter(t -> t.getUserId() == userId && t.getRoomId() == roomId)
                .toList();

        double sum = 0;
        for (int i = 0; i < attempts.size(); i++) {
            sum += attempts.get(i).getGrade();
        }

        if (sum == 0) {
            return -1;
        }

        double average = sum / attempts.size();
        return Math.round(average * 10) / 10.0;
    }

    public TestAttempt getTestAttempt(int testId, int userId) throws TestNotFoundException {
        List<TestAttempt> attempts = jsonService.getAll("test_attempts.json", TestAttempt.class);
        return attempts
                .stream()
                .filter(t -> t.getTestId() == testId && t.getUserId() == userId)
                .findFirst()
                .orElseThrow(() -> new TestNotFoundException("test not found!"));
    }


    public List<TestAttempt> getAllTeachersAssignedTests(int userId) throws TestNotFoundException {
        List<TestAttempt> testAttempts = jsonService.getAll("test_attempts.json", TestAttempt.class);
        List<TestAttempt> result = new ArrayList<>();
        for(int i = 0; i<testAttempts.size(); i++){
            TestAttempt testAttempt = testAttempts.get(i);
            TestModel testModel = getTestById(testAttempt.getTestId());
            if(testModel.getTeacherId() == userId){
                result.add(testAttempt);
            }
        }
        return result;
    }

    public List<TestModel> getAllTeachersCompletedTestsinRoom(int roomId, int userId){
        return jsonService.getAll("tests.json", TestModel.class)
                .stream()
                .filter(t -> t.getRoomId() == roomId && t.getTestStatus().equals(TestStatus.COMPLETE) && t.getTeacherId() == userId)
                .toList();
    }

}
