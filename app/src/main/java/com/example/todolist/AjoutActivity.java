package com.example.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CalendarView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.todolist.TachesBD.TableEntry;

import java.util.ArrayList;
import java.util.List;

public class AjoutActivity extends AppCompatActivity{
    private TachesDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ajout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.dbHelper = new TachesDBHelper(this);

        Button boutonAdd = findViewById(R.id.btn_addTask);
        boutonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameTask = ((EditText) findViewById(R.id.textNomTask)).getText().toString();

                String descTask = ((EditText) findViewById(R.id.textDescTask)).getText().toString();

                RadioGroup radioGroup = findViewById(R.id.radioGroupPrio);
                int priorityID = radioGroup.getCheckedRadioButtonId();
                RadioButton prioritySelect = findViewById(priorityID);
                String priorityTask = prioritySelect.getText().toString();
                int prio = 0;
                switch(priorityTask){
                    case "High":
                        prio = 2;
                        break;
                    case "Normal" :
                        prio = 1;
                        break;
                }

                CalendarView calendrier = findViewById(R.id.calendar);
                long selectedDateMillis = calendrier.getDate();

                Calendar selectedCalendar = Calendar.getInstance();
                selectedCalendar.setTimeInMillis(selectedDateMillis);

                int year = selectedCalendar.get(Calendar.YEAR);
                int month = selectedCalendar.get(Calendar.MONTH) + 1;
                int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);

                String dateTask = year + "-" + month + "-" + day;

                // Ajouter une ligne à la base de donnée
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(TableEntry.COLUMN_NAME_NAME, nameTask);
                values.put(TableEntry.COLUMN_NAME_DESCRIPTION, descTask);
                values.put(TableEntry.COLUMN_NAME_PRIORITY, prio);
                values.put(TableEntry.COLUMN_NAME_DATE, dateTask);
                values.put(TableEntry.COLUMN_NAME_DONE, 0);

                long newRowId = db.insert(TableEntry.TABLE_NAME, null, values);

                //Afficher le contenu de la base de donnée dans la console

                db = dbHelper.getReadableDatabase();

                String[] projection = {
                        TableEntry._ID,
                        TableEntry.COLUMN_NAME_NAME,
                        TableEntry.COLUMN_NAME_DESCRIPTION,
                        TableEntry.COLUMN_NAME_PRIORITY,
                        TableEntry.COLUMN_NAME_DATE,
                        TableEntry.COLUMN_NAME_DONE
                };

                Cursor cursor = db.query(
                        TableEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                List<Long> contenuBase = new ArrayList<>();
                while (cursor.moveToNext()) {
                    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(TableEntry._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(TableEntry.COLUMN_NAME_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(TableEntry.COLUMN_NAME_DESCRIPTION));
                    int priority = cursor.getInt(cursor.getColumnIndexOrThrow(TableEntry.COLUMN_NAME_PRIORITY));
                    String date = cursor.getString(cursor.getColumnIndexOrThrow(TableEntry.COLUMN_NAME_DATE));
                    int done = cursor.getInt(cursor.getColumnIndexOrThrow(TableEntry.COLUMN_NAME_DONE));

                    System.out.println("ID: " + itemId + ", Name: " + name + ", Description: " + description +
                            ", Priority: " + priority + ", Date: " + date + ", Done: " + done);

                    contenuBase.add(itemId);
                }
                cursor.close();

            }
        });
    }
};
