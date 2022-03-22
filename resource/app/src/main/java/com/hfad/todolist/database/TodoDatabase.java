package com.hfad.todolist.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.hfad.todolist.dao.ProjectDao;
import com.hfad.todolist.models.Project;

@Database(entities = {Project.class}, version=1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    private static TodoDatabase todoDatabase;

    public static synchronized TodoDatabase getTodoDatabase(Context context) {
        if(todoDatabase == null) {
            todoDatabase = Room.databaseBuilder(
                    context,
                    TodoDatabase.class,
                    "todo_database").build();
        }
        return todoDatabase;
    }

    public abstract ProjectDao projectDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
