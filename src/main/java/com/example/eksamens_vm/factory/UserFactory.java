package com.example.eksamens_vm.factory;

import com.example.eksamens_vm.models.Student;
import com.example.eksamens_vm.models.Teacher;
import com.example.eksamens_vm.models.User;

public class UserFactory {

    public static User createUser(User user) {
        if(user == null) {
            return null;
        }

        return switch(user.getUserRole()){
            case STUDENT -> new Student(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserRole(),
                    "DP2-1"
            );
            case TEACHER -> new Teacher(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserRole()
            );
        };
    }
}
