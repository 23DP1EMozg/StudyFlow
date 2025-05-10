package com.example.eksamens_vm.services;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.exceptions.NotFoundException;
import com.example.eksamens_vm.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonService {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public <T> void save(T object, String path, Class<T> clazz){

        List<T> list = getAll(path, clazz);
        list.add(object);
        saveMany(list, path);

    }

    public <T> void saveMany(List<T> objects, String path) {
        Path filePath = Paths.get("src/main/resources/data").resolve(path);

        try(FileWriter fw = new FileWriter(filePath.toFile())) {

            gson.toJson(objects, fw);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T> List<T> getAll(String path, Class<T> clazz){
        Path filePath = Paths.get("src/main/resources/data").resolve(path);
        File file = filePath.toFile();
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader fr = new FileReader(file)) {
            Type listType = TypeToken.getParameterized(List.class, clazz).getType();

            List<T> result = gson.fromJson(fr, listType);
            return (result != null) ? result : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
