
package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    TasksDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistiques);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.db = new TasksDBHelper(this);
        ImageView btnReturn = findViewById(R.id.btnBack);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TableLayout table = findViewById(R.id.tableStats);
        Map<String, List<Integer>> stats = this.db.tasksByDate();
        for (Map.Entry<String, List<Integer>> line : stats.entrySet()) {
            String date = line.getKey();
            List<Integer> counts = line.getValue();

            // Create a new TableRow
            TableRow row = new TableRow(this);

            // Create TextViews to hold the data
            TextView dateTextView = new TextView(this);
            dateTextView.setText(date);
            row.addView(dateTextView);

            TextView count1TextView = new TextView(this);
            count1TextView.setText(String.valueOf(counts.get(0))); // Assuming the first int is count(status==1)
            row.addView(count1TextView);

            TextView count0TextView = new TextView(this);
            count0TextView.setText(String.valueOf(counts.get(1))); // Assuming the second int is count(status==0)
            row.addView(count0TextView);

            // Add the TableRow to the TableLayout
            table.addView(row);
        }

    }


}
