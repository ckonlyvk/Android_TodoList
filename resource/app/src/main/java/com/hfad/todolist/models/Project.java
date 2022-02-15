package com.hfad.todolist.models;

import java.util.UUID;

public class Project {
    UUID mId;
    String mTitle;
    String mDescription;

    public Project(UUID id) {
        mId = id;
    }

    public Project(String nameProject,String description) {
        mId = UUID.randomUUID();
        mTitle = nameProject;
        mDescription = description;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }
}
