package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import static com.example.todolist.TasksDB.TableEntry.*;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<ListModel> {
    private Context context;
    private List<ListModel> lignes;

    public CustomListAdapter(Context context, List<ListModel> lignes){
        super(context, R.layout.list_custom, lignes);
        this.context = context;
        this.lignes = lignes;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View finalView = view;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_custom, null);
        }

        ListModel ligne = lignes.get(position);
        if (ligne != null){
            TextView taskName = view.findViewById(R.id.detailTask);
            Switch taskStatus = view.findViewById(R.id.taskStatus);

            taskName.setText(ligne.getNomTache());
            taskStatus.setChecked(ligne.getTacheTermine());

            taskStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                ligne.setTacheTermine(isChecked);

                TasksDBHelper dbHelper = new TasksDBHelper(context);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if (isChecked){
                    values.put(COLUMN_NAME_DONE, 1);
                }else{
                    values.put(COLUMN_NAME_DONE, 0);
                }
                String selection = COLUMN_NAME_NAME + " LIKE ?";
                String[] selectionArgs = {ligne.getNomTache()};

                int count = db.update(
                        TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
            });
        }
        return view;
    }

}
