package com.hfad.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hfad.todolist.database.TodoDBSchema.TaskTable;

public class TaskBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;

    public TaskBaseHelper(@Nullable Context context) {
        super(context, TodoDBSchema.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TaskTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskTable.Cols.UUID + ", " +
                TaskTable.Cols.TITLE + "," +
                TaskTable.Cols.DESCRIPTION + "," +
                TaskTable.Cols.DEAD_LINE + "," +
                TaskTable.Cols.IS_COMPLETED + "," +
                TaskTable.Cols.PROJECT_ID +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
