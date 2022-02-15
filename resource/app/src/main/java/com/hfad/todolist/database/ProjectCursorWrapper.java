package com.hfad.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hfad.todolist.database.TodoDBSchema.ProjectTable;
import com.hfad.todolist.models.Project;

import java.util.UUID;

public class ProjectCursorWrapper extends CursorWrapper {
    public ProjectCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Project getProject() {
        String uuidString = getString(getColumnIndex(ProjectTable.Cols.UUID));
        String title = getString(getColumnIndex(ProjectTable.Cols.TITLE));
        String description = getString(getColumnIndex(ProjectTable.Cols.DESCRIPTION));

        Project project = new Project(UUID.fromString(uuidString));
        project.setTitle(title);
        project.setDescription(description);

        return project;
    }
}
