package com.hfad.todolist.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "project")
public class Project implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    UUID mId;

    @SerializedName("title")
    String mTitle;

    @SerializedName("description")
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
