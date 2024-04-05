package com.example.todolist;

import static com.example.todolist.TasksDB.TableEntry.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TasksDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "TacheDB.db";
//    public static final String TABLE_NAME = "Taches";
//    public static final String COLUMN_ID = "ID";
//    public static final String COLUMN_NAME = "nom";
//    public static final String COLUMN_DESCRIPTION = "description";
//    public static final String COLUMN_PRIORITY = "priorité";
//    public static final String COLUMN_DATE = "date";
//    public static final String COLUMN_DONE = "fait";
    public static final String SQL_CREATE_ENTRIES ="CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_NAME + " TEXT," +
            COLUMN_NAME_DESCRIPTION + " TEXT," +
            COLUMN_NAME_PRIORITY + " INTEGER," +
            COLUMN_NAME_DATE + " TEXT," +
            COLUMN_NAME_DONE + " INTEGER)";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
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
    public boolean addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME_NAME, task.getNom());
        cv.put(COLUMN_NAME_DATE, task.getDate());
        cv.put(COLUMN_NAME_DESCRIPTION, task.getDesc());
        cv.put(COLUMN_NAME_PRIORITY, task.getPriorite());
        cv.put(COLUMN_NAME_DONE, task.isStatut());
        long res = db.insert(DATABASE_NAME, null, cv);
        return res != -1;
    }

    public boolean deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        String st = "DELETE FROM " + TABLE_NAME + " WHERE " + _ID + "=" + task.getId();
        Cursor cursor = db.rawQuery(st, null);
        return cursor.moveToFirst();
    }

    public List<Task> getTasks(){
        List<Task> tasks = new LinkedList<>();
        String st = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(st, null);
        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                String priority = cursor.getString(3);
                String date = cursor.getString(4);
                boolean status = cursor.getInt(5) == 1 ? true : false;
                Task task = new Task(id, name, desc, priority, date);
                task.setStatut(status);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public Task getTaskById(int id){
        return this.getTasks().stream().filter(e->e.getId()==id).collect(Collectors.toList()).get(0);
    }

    public Map<String, List<Integer>> tasksByDate(){
        String st = "SELECT " + COLUMN_NAME_DATE + ", " +  "SUM(CASE WHEN " + COLUMN_NAME_DONE + " = 1 THEN 1 ELSE 0 END), "
                + "SUM(CASE WHEN " + COLUMN_NAME_DONE + "= 0 THEN 1 ELSE 0 END) "
                + "FROM " + TABLE_NAME +" "
                + "GROUP BY " + COLUMN_NAME_DATE + " "
                + "ORDER BY " + COLUMN_NAME_DATE;
//                + "SUBSTR(" + COLUMN_NAME_DATE + ", 7, 4) || '-' || "
//                + "SUBSTR(" + COLUMN_NAME_DATE + ", 4, 2) || '-' || "
//                + "SUBSTR(" + COLUMN_NAME_DATE + ", 1, 2)";
        Map<String, List<Integer>> res = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(st, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                List<Integer> tasks = new LinkedList<>();
                tasks.add(cursor.getInt(1));
                tasks.add(cursor.getInt(2));
                res.put(date, tasks);
            } while (cursor.moveToNext());
        }
        Log.d("My order", res.toString());
        return res;
    }
}
