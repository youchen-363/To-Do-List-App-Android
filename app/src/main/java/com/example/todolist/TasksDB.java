package com.example.todolist;

import android.provider.BaseColumns;

public final class TasksDB {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TasksDB() {}

    /* Inner class that defines the table contents */
    public static class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "Taches";
        public static final String COLUMN_NAME_NAME = "nom";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PRIORITY = "priorité";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_DONE = "fait";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                    TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TableEntry.COLUMN_NAME_NAME + " TEXT," +
                    TableEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    TableEntry.COLUMN_NAME_PRIORITY + " INTEGER," +
                    TableEntry.COLUMN_NAME_DATE + " TEXT," +
                    TableEntry.COLUMN_NAME_DONE + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;
}