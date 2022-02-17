package com.hfad.todolist.database;

public class TodoDBSchema {
    public static final String DATABASE_NAME = "TodoDB";

    public static final class ProjectTable {
        public static final String NAME = "PROJECT";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
        }
    }
    public static final class TaskTable {
        public static final String NAME = "TASK";
        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DEAD_LINE = "deal_line";
            public static final String IS_COMPLETED = "is_completed";
            public static final String PROJECT_ID = "project_id";
        }
    }
}
