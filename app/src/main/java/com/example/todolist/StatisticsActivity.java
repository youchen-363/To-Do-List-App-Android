package com.example.todolist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
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

    private TasksDBHelper db;
    private TableLayout table;
    private TableLayout tableHeader;
    private TableRow tableHeaderRow;
    private TextView headerDate;
    private TextView headerDone;
    private TextView headerTotal;

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

        table = findViewById(R.id.tableStats);
//        tableHeader = findViewById(R.id.tableHeader);
//        tableHeaderRow = findViewById(R.id.tableHeaderRow);
        Map<String, List<Integer>> stats = this.db.tasksByDate();
        System.out.println(stats.toString());
        for (Map.Entry<String, List<Integer>> line : stats.entrySet()) {
            String date = DateFormatter.dateFormatFrench(line.getKey());
            List<Integer> counts = line.getValue();

            TableRow row = new TableRow(this);
            /*
            this.headerDate = findViewById(R.id.tableHeaderDate);
            this.headerDone = findViewById(R.id.tableHeaderDone);
            this.headerTotal = findViewById(R.id.tableHeaderTotal);
            */
//            TextView dateTextView = createTextViewFormatted(this, date, this.headerDate.getWidth());
            TextView dateTextView = createTextViewFormatted(this, date, 0);
            row.addView(dateTextView);

//            TextView done = createTextViewFormatted(this, String.valueOf(counts.get(0)), this.headerDone.getWidth());
            TextView done = createTextViewFormatted(this, String.valueOf(counts.get(0)), 0);
            row.addView(done);

//            TextView total = createTextViewFormatted(this, String.valueOf(counts.get(1)), this.headerTotal.getWidth());
            TextView total = createTextViewFormatted(this, String.valueOf(counts.get(1)), 0);
            row.addView(total);

            table.addView(row);
            System.out.println("Im here " + line.toString());
        }
        System.out.println("why am i closed ");
    }

    public TextView createTextViewFormatted(Context context, String content, int width) {
        TextView textView = new TextView(context);
        textView.setText(content);
        // Set text alignment to center
        textView.setGravity(Gravity.CENTER);
//        textView.setWidth(width);
        // Set background drawable (assuming you have a drawable resource named "table_border")
        textView.setBackground(context.getDrawable(R.drawable.table_border));

        // Set text size in sp (scaled pixels)
        textView.setTextSize(15);  // Size in scaled pixels

        // Set padding vertically (5dp)
        textView.setPadding(0, 5, 0, 5);

        System.out.println("Create a text view successful");
        // Return the configured TextView
        return textView;
    }

}