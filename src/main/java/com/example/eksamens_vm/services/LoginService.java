package com.example.eksamens_vm.services;

import com.example.eksamens_vm.data.Session;
import com.example.eksamens_vm.exceptions.InvalidCredentialsException;
import com.example.eksamens_vm.exceptions.InputFieldEmptyException;
import com.example.eksamens_vm.exceptions.UserNotFoundException;
import com.example.eksamens_vm.models.User;



public class LoginService {

    private UserService userService = new UserService();
    private Session session = Session.getInstance();

    public User login(String username, String password) throws UserNotFoundException, InvalidCredentialsException, InputFieldEmptyException {

        User user = userService.getUserByUsername(username);

       if(username.isBlank() || password.isBlank()){
           throw new InputFieldEmptyException("cannot leave fields empty");
       }

       if(user == null){
           throw new UserNotFoundException("user with that username doesnt exist!");
       }

       if(!user.getPassword().equals(password)){
           throw new InvalidCredentialsException("incorrect password");
       }


       session.setLoggedInUser(user);

       return user;
    }
}
