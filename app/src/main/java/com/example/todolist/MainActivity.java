package com.example.todolist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import static com.example.todolist.TasksDB.TableEntry.*;

public class MainActivity extends AppCompatActivity {
    private TasksDBHelper dbHelper;
    private CustomListAdapter adapter;
    private List<ModeleListe> lignesListe;
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
        ListView listeTaches = findViewById(R.id.listView);
        this.lignesListe = new ArrayList<>();
        this.adapter = new CustomListAdapter(this, lignesListe);
        listeTaches.setAdapter(adapter);

        // Affichage des enregistrements de la BD dans la liste
        afficherListeTaches();

        // Bouton ajouter une tâche
        Button boutonAdd = findViewById(R.id.btnAdd);
        boutonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
        setMenuBackgroundColor(R.color.white);

    }

    private void addTask(){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        try {
            startActivityForResult(intent, 100);
        }catch(ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    public void setMenuBackgroundColor(int color){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(color)));
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
        switch(id) {
            case R.id.info_menu :
                addTask();
                return true;
            case R.id.quit_menu :
                System.exit(0);
                return true;
            case R.id.view_stats:
                intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, data.getStringExtra("valeur"), Toast.LENGTH_SHORT).show();
                afficherListeTaches();
            }else{
                Toast.makeText(MainActivity.this, getString(R.string.task_cancel), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void afficherListeTaches() {
        lignesListe.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                _ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_DESCRIPTION,
                COLUMN_NAME_PRIORITY,
                COLUMN_NAME_DATE,
                COLUMN_NAME_DONE
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NAME));
            int done = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NAME_DONE));
            boolean state = false;
            System.out.println(name+" : "+done+" : "+itemId);
            if (done == 1){
                state = true;
            }
            lignesListe.add(new ModeleListe(name, state));
            adapter.notifyDataSetChanged();
        }
        cursor.close();
    }
};

