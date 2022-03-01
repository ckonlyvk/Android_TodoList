package com.hfad.todolist.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hfad.todolist.activities.TaskActivity;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.Task;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class TaskViewModel extends BaseObservable {
    private Task mTask;

    TaskViewModel(Task task) {
        mTask = task;
    }

    public TaskViewModel() {
    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        mTask = task;
        notifyChange();
    }

    @Bindable
    public UUID getId() {
        return mTask.getId();
    }

    @Bindable
    public String getTitle() {
        return mTask.getTitle();
    }

    @Bindable
    public String getDescription() {
        return mTask.getDescription();
    }

    @Bindable
    public String getDate() {
        if(mTask.getDeadline() == null) return "No Date";
        return new SimpleDateFormat("MMM dd, YYYY").format(mTask.getDeadline());
    }

    @Bindable
    public String getTime() {
        if(mTask.getDeadline() == null) return "No Time";
        return new SimpleDateFormat("HH : mm").format(mTask.getDeadline());
    }
}
