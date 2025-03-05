package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.InvalidCredentialsException;
import com.example.eksamens_vm.exceptions.UserFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.Student;
import com.example.eksamens_vm.models.User;



public class LoginService {

    private UserService userService = new UserService();
    private Session session = Session.getInstance();

    public void login(String username, String password) throws UserNotFoundException, InvalidCredentialsException, UserFieldEmptyException {

        User user = (Student)userService.getUserByUsername(username);

       if(username.isBlank() || password.isBlank()){
           throw new UserFieldEmptyException("cannot leave fields empty");
       }

       if(user == null){
           throw new UserNotFoundException("user with that username doesnt exist!");
       }

       if(!user.getPassword().equals(password)){
           throw new InvalidCredentialsException("incorrect password");
       }

       if(user.getUserRole() == UserRole.STUDENT){
           User student = new Student(
                   user.getId(),
                   user.getUsername(),
                   user.getPassword(),
                   user.getUserRole(),
                   "DP2-1"
           );

           ((Student) student).getGroup();
       }

       session.setLoggedInUser(user);
    }
}
