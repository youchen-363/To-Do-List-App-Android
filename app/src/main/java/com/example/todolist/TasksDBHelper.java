package com.example.todolist;

import static com.example.todolist.TasksBD.SQL_CREATE_ENTRIES;
import static com.example.todolist.TasksBD.SQL_DELETE_ENTRIES;
import static com.example.todolist.TasksBD.TableEntry.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TasksDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TacheDB.db";

    public TasksDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Map<String, List<Integer>> tasksByDate(){
        String st = "SELECT " + COLUMN_NAME_DATE + ", " +  "SUM(CASE WHEN " + COLUMN_NAME_DONE + " = 1 THEN 1 ELSE 0 END), "
                + "SUM(CASE WHEN " + COLUMN_NAME_DONE + "= 0 THEN 1 ELSE 0 END) "
                + "FROM " + TABLE_NAME +" "
                + "GROUP BY " + COLUMN_NAME_DATE;
        Map<String, List<Integer>> res = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(st, null);
        while(cursor.moveToFirst()) {
            String date = cursor.getString(0);
            List<Integer> tasks = new LinkedList<>();
            tasks.add(cursor.getInt(1));
            tasks.add(cursor.getInt(2));
            res.put(date, tasks);
        }
        return res;
    }
}
