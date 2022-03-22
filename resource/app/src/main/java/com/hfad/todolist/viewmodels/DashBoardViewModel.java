package com.hfad.todolist.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hfad.todolist.database.TodoDatabase;
import com.hfad.todolist.models.Project;

import io.reactivex.Completable;

public class DashBoardViewModel extends AndroidViewModel {
    private TodoDatabase todoDatabase;

    public DashBoardViewModel(@NonNull Application application) {
        super(application);
        todoDatabase = TodoDatabase.getTodoDatabase(application);
    }

    public Completable addProject(Project project) {
        return todoDatabase.projectDao().addProject(project);
    }
}
