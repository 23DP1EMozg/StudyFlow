package com.example.eksamens_vm.utils;

public class TypeCheckManager {

    public boolean isNumeric(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
