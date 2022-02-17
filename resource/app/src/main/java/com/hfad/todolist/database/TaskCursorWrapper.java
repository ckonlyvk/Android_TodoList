package com.hfad.todolist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.hfad.todolist.database.TodoDBSchema.TaskTable;
import com.hfad.todolist.models.Project;
import com.hfad.todolist.models.Task;

import java.util.Date;
import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    
    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskTable.Cols.TITLE));
        String description = getString(getColumnIndex(TaskTable.Cols.DESCRIPTION));
        long dealine = getLong(getColumnIndex(TaskTable.Cols.DESCRIPTION));
        int isCompleted = getInt(getColumnIndex(TaskTable.Cols.DEAD_LINE));
        String projectId = getString(getColumnIndex(TaskTable.Cols.IS_COMPLETED));

        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(new Date(dealine));
        task.setIsCompleted(isCompleted != 0);
        task.setProjectId(UUID.fromString(projectId));

        return task;
    }
}
