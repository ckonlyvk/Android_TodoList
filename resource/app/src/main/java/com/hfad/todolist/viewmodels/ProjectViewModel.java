package com.hfad.todolist.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hfad.todolist.activities.TaskActivity;
import com.hfad.todolist.models.Project;

import java.util.UUID;


public class ProjectViewModel extends BaseObservable {
    private Project mProject;

    ProjectViewModel(Project project) {
        mProject = project;
    }

    public ProjectViewModel() {
    }

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        mProject = project;
        notifyChange();
    }

    @Bindable
    public UUID getId() {
        return mProject.getId();
    }

    @Bindable
    public String getTitle() {
        return mProject.getTitle();
    }

    @Bindable
    public String getDescription() {
        return mProject.getDescription();
    }

    public void onClick(View view, UUID id) {
        Context context = view.getContext();
        Intent intent = TaskActivity.newIntent(context, id);
        context.startActivity(intent);
    }
}
