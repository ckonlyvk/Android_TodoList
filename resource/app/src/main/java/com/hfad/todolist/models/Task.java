package com.hfad.todolist.models;

import java.util.Date;
import java.util.UUID;

public class Task {
    UUID mId;
    String mTitle;
    String mDescription;
    Date mDeadline;
    boolean mIsCompleted;
    UUID mProjectId;

    public Task(UUID id) {
        mId = id;
    }

    public Task() {
        mId = UUID.randomUUID();
        mIsCompleted = false;
    }

    public Task(String mTitle, String mDescription) {
        this();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public Task(String mTitle, String mDescription, Date deadline) {
        this();
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDeadline = deadline;
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

    public Date getDeadline() {
        return mDeadline;
    }

    public void setDeadline(Date deadline) {
        this.mDeadline = deadline;
    }

    public boolean isIsCompleted() {
        return mIsCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.mIsCompleted = isCompleted;
    }

    public UUID getProjectId() {
        return mProjectId;
    }

    public void setProjectId(UUID projectId) {
        this.mProjectId = projectId;
    }
}
