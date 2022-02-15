package com.hfad.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hfad.todolist.database.TodoDBSchema.ProjectTable;

public class ProjectBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;

    public ProjectBaseHelper(@Nullable Context context) {
        super(context, TodoDBSchema.DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + ProjectTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ProjectTable.Cols.UUID + ", " +
                ProjectTable.Cols.TITLE + "," +
                ProjectTable.Cols.DESCRIPTION +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
