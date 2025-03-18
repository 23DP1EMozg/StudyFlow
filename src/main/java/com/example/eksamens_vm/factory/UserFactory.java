package com.example.eksamens_vm.factory;

import com.example.eksamens_vm.models.Student;
import com.example.eksamens_vm.models.Teacher;
import com.example.eksamens_vm.models.User;

import java.util.List;

public class UserFactory {

    public static User createUser(User user) {
        if(user == null) {
            return null;
        }

        return switch(user.getUserType()){
            case STUDENT -> new Student(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserType(),
                    null,
                    List.of()
            );
            case TEACHER -> new Teacher(
                    user.getId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getUserType(),
                    List.of()
            );
        };
    }
}
