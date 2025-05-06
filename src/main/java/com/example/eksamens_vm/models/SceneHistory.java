package com.example.eksamens_vm.models;

public class SceneHistory {
    private String fxmlFile;
    private String title;

    public SceneHistory(String fxmlFile, String title) {
        this.fxmlFile = fxmlFile;
        this.title = title;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }

    public void setFxmlFile(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "SceneHistory{" +
                "fxmlFile='" + fxmlFile + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
