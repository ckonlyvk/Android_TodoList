package com.hfad.todolist.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hfad.todolist.database.ProjectCursorWrapper;
import com.hfad.todolist.database.TaskBaseHelper;
import com.hfad.todolist.database.TaskCursorWrapper;
import com.hfad.todolist.database.TodoBaseHelper;
import com.hfad.todolist.database.TodoDBSchema;
import com.hfad.todolist.database.TodoDBSchema.TaskTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TaskList {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static TaskList mTaskList;
    List<Task> mTasks;

    private TaskList(Context context) {
        mTasks = new ArrayList<Task>();
        mContext = context.getApplicationContext();
        mDatabase = new TodoBaseHelper(mContext)
                .getWritableDatabase();
    }

    public static TaskList getInstance(Context context) {
        if(mTaskList == null) {
            mTaskList = new TaskList(context);
        }

        return mTaskList;
    }

    private static ContentValues getContentValues(Task task) {
        //ContentValues là một lớp lưu trữ tương tự HashMap (Key và Value)
        //Nhưng nó được sử dụng cụ thể cho việc lưu dữ liệu cho SQLite
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.UUID, task.getId().toString());
        values.put(TaskTable.Cols.TITLE, task.getTitle());
        values.put(TaskTable.Cols.DESCRIPTION, task.getDescription());
        values.put(TaskTable.Cols.IS_COMPLETED, task.isIsCompleted() ? 1 : 0);
        if(task.getProjectId() != null) {
            values.put(TaskTable.Cols.PROJECT_ID, task.getProjectId().toString());
        }
        else {
            values.putNull(TaskTable.Cols.PROJECT_ID);
        }
        if(task.getDeadline() != null) {
            values.put(TaskTable.Cols.DEAD_LINE, task.getDeadline().getTime());
        }
        else {
            values.putNull(TaskTable.Cols.DEAD_LINE);
        }

        return values;
    }

    public void addTask(Task task) {
        ContentValues value = getContentValues(task);
        mDatabase.insert(TaskTable.NAME, null, value);
    }

    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return new TaskCursorWrapper(cursor);
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public List<Task> getTasksByProjectId(UUID projectId) {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(
                TaskTable.Cols.PROJECT_ID + " = ? ",
                new String[] {projectId.toString()});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public List<Task> getTasksByDate(Date date) {
        List<Task> tasks = new ArrayList<>();
        TaskCursorWrapper cursor = queryTasks(
                TaskTable.Cols.DEAD_LINE + " = ? ",
                new String[] {String.valueOf(date.getTime())});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getTask());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public void updateTask(Task task) {
        String uuidString = task.getId().toString();
        ContentValues values = getContentValues(task);

        //Tham số 3 là mệnh đề where
        //Tham số 4 là mảng các phần tử chuỗi được thế vào dấu ? tại tham số 3
        //Where and arg clause
        mDatabase.update(TaskTable.NAME, values,
                TaskTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public int countTask(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TaskTable.NAME,
                new String[]{TaskTable.Cols.UUID}, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );

        return cursor.getCount();
    }
}
