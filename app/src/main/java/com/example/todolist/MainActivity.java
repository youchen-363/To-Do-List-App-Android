package com.example.todolist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TasksDBHelper dbHelper;
    private CustomListAdapter adapter;
    private List<ListModel> listRows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.dbHelper = new TasksDBHelper(this);

        ListView tasksList = findViewById(R.id.tasksList);
        this.listRows = new ArrayList<>();

        this.adapter = new CustomListAdapter(this, listRows);
        tasksList.setAdapter(adapter);

        Button boutonAdd = findViewById(R.id.btnAdd);
        boutonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        displayTasksList();

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                ListModel model = listRows.get(position);
                Task task = model.getTache();
                intent.putExtra("Tache", task);
                startActivity(intent);
            }
        });
    }

    private void addTask(){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        try {
            startActivityForResult(intent, 100);
        }catch(ActivityNotFoundException e){
            Log.e("ActivityNotFoundException", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.monmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.info_menu) {
            addTask();
            return true;
        } else if (id == R.id.quit_menu) {
            System.exit(0);
            return true;
        } else if (id == R.id.view_stats) {
            intent = new Intent(MainActivity.this, StatisticsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, data.getStringExtra("valeur"), Toast.LENGTH_SHORT).show();
                displayTasksList();
            }else{
                Toast.makeText(MainActivity.this, data.getStringExtra("valeur"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayTasksList() {
        listRows.clear();
        List<Task> tasks = this.dbHelper.getTasks();
        for (Task task : tasks) {
            listRows.add(new ListModel(task, task.isStatut()));
        }
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        displayTasksList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTasksList();
    }
};

