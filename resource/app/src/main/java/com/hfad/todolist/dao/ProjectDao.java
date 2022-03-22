package com.hfad.todolist.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.hfad.todolist.models.Project;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project")
    Flowable<List<Project>> getProjects();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addProject(Project project);

    @Delete
    Completable removeProject(Project project);
//
//    @Query("SELECT * FROM tvShows WHERE id = :tvShowId")
//    Flowable<TVShow> getTVShowFromWatchlist(String tvShowId);
}
