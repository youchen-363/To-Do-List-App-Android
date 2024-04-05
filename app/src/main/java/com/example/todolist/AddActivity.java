package com.example.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import static com.example.todolist.TasksDB.TableEntry.*;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity{
    private TasksDBHelper dbHelper;
    private CalendarView calender;
    private String dateSelected;

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
        this.calender = findViewById(R.id.calendar);
        dateSelected = DateFormatter.todayDate();
        this.calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int monthSelected = month+1;
                dateSelected = DateFormatter.toDate(year, monthSelected, dayOfMonth);
                Log.d("My date", dateSelected);
            }
        });

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

                if (!nameTask.isEmpty()){
                    Task task = new Task(-1, nameTask, descTask, prio, dateSelected);
                    dbHelper.addTask(task);

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
