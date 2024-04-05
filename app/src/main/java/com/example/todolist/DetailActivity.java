package com.example.todolist;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Task tache = (Task) getIntent().getSerializableExtra("Tache");
        TextView fieldName = findViewById(R.id.fieldName);
        TextView fieldDesc = findViewById(R.id.fieldDesc);
        TextView fieldPrio = findViewById(R.id.fieldPriority);
        TextView fieldDate = findViewById(R.id.fieldDate);

        fieldName.setText(tache.getNom());
        fieldDesc.setText(tache.getDesc());
        fieldPrio.setText(tache.prioriteString(this));
        fieldDate.setText(DateFormatter.dateFormatFrench(tache.getDate()));

        TasksDBHelper dbHelper = new TasksDBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Button boutonDelete = findViewById(R.id.btn_Delete);
        boutonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage(R.string.confirm_message)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // OUI
                                String selection = TasksDB.TableEntry._ID + " LIKE ?";
                                String[] selectionArgs = { String.valueOf(tache.getId()) };
                                int deletedRows = db.delete(TasksDB.TableEntry.TABLE_NAME, selection, selectionArgs);
                                Toast.makeText(DetailActivity.this,  getString(R.string.task_deleted), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // NON
                                Toast.makeText(DetailActivity.this, getString(R.string.task_delete_cancel), Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.create().show();
            }
        });

        ImageView boutonBack = findViewById(R.id.btnBack);
        boutonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
