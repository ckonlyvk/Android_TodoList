package com.hfad.todolist.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.hfad.todolist.database.TodoDatabase;
import com.hfad.todolist.models.Project;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class ProjectsViewModel extends AndroidViewModel {
    private TodoDatabase todoDatabase;

    public ProjectsViewModel(@NonNull Application application) {
        super(application);
        todoDatabase = TodoDatabase.getTodoDatabase(application);
    }

    public Flowable<List<Project>> loadProject() {
        return todoDatabase.projectDao().getProjects();
    }

    public Completable removeTVShowFromWatchlist(Project project) {
        return todoDatabase.projectDao().removeProject(project);
    }
}
