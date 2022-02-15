package com.hfad.todolist;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.hfad.todolist.models.Project;


public class ProjectViewModel extends BaseObservable {
    private Project mProject;

    ProjectViewModel(Project project) {
        mProject = project;
    }

    ProjectViewModel() {
    }

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        mProject = project;
        notifyChange();
    }

    @Bindable
    public String getTitle() {
        return mProject.getTitle();
    }

    @Bindable
    public String getDescription() {
        return mProject.getDescription();
    }
}
