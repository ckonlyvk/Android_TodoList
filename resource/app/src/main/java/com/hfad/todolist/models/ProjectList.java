package com.hfad.todolist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.todolist.database.ProjectBaseHelper;
import com.hfad.todolist.database.ProjectCursorWrapper;
import com.hfad.todolist.database.TodoDBSchema;
import com.hfad.todolist.database.TodoDBSchema.ProjectTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectList {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static ProjectList mProjectList;
    List<Project> mProjects;

    private ProjectList(Context context) {
        mProjects = new ArrayList<Project>();
        mContext = context.getApplicationContext();
        mDatabase = new ProjectBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static ProjectList getInstance(Context context) {
        if(mProjectList == null) {
            mProjectList = new ProjectList(context);
        }

        return mProjectList;
    }

    private static ContentValues getContentValues(Project project) {
        //ContentValues là một lớp lưu trữ tương tự HashMap (Key và Value)
        //Nhưng nó được sử dụng cụ thể cho việc lưu dữ liệu cho SQLite
        ContentValues values = new ContentValues();
        values.put(ProjectTable.Cols.UUID, project.getId().toString());
        values.put(ProjectTable.Cols.TITLE, project.getTitle());
        values.put(ProjectTable.Cols.DESCRIPTION, project.getDescription());
        return values;
    }

    public void addProject(Project project) {
        ContentValues values = getContentValues(project);
        mDatabase.insert(ProjectTable.NAME, null, values);
    }

    public void updateProject(Project project) {
        ContentValues values = getContentValues(project);
        mDatabase.update(
                ProjectTable.NAME,
                values,
                ProjectTable.Cols.UUID + " = ?",
                new String[] { project.getId().toString()});
    }

    public void deleteProject(UUID projectId) {
        mDatabase.delete(
                ProjectTable.NAME,
                ProjectTable.Cols.UUID + " = ?",
                new String[] { projectId.toString()});
    }

    private ProjectCursorWrapper queryProjects(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ProjectTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new ProjectCursorWrapper(cursor);
    }

    public List<Project> getProjects() {
        List<Project> projects = new ArrayList<>();
        ProjectCursorWrapper cursor = queryProjects(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                projects.add(cursor.getProject());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return projects;
    }

    public Project getProject(UUID id) {
        ProjectCursorWrapper cursor = queryProjects(
            ProjectTable.Cols.UUID + " = ?",
            new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getProject();
        } finally {
            cursor.close();
        }
    }
}
