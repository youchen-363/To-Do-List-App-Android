package com.example.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CalendarView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.todolist.TasksBD.TableEntry;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity{
    private TasksDBHelper dbHelper;
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
        this.dbHelper = new TasksDBHelper(this);

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
                if (!nameTask.isEmpty()){
                    // Ajouter une ligne à la base de donnée
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(TableEntry.COLUMN_NAME_NAME, nameTask);
                    values.put(TableEntry.COLUMN_NAME_DESCRIPTION, descTask);
                    values.put(TableEntry.COLUMN_NAME_PRIORITY, prio);
                    values.put(TableEntry.COLUMN_NAME_DATE, dateTask);
                    values.put(TableEntry.COLUMN_NAME_DONE, 0);

                    long newRowId = db.insert(TableEntry.TABLE_NAME, null, values);

                    Intent retour = new Intent();
                    retour.putExtra("valeur",getString(R.string.task_added));
                    setResult(RESULT_OK, retour);
                    finish();
                }else{
                    Toast.makeText(AddActivity.this, getString(R.string.task_incomplete), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageView boutonBack = findViewById(R.id.btnBack);
        boutonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent();
                retour.putExtra("valeur",getString(R.string.task_cancel));
                setResult(RESULT_CANCELED, retour);
                finish();
            }
        });
    }
};
