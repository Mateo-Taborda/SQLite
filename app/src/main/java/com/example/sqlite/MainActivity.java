package com.example.sqlite;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonAddData;
    private Button buttonRetrieveData;
    private ListView listViewData;

    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        buttonAddData = findViewById(R.id.buttonAddData);
        buttonRetrieveData = findViewById(R.id.buttonRetrieveData);
        listViewData = findViewById(R.id.listViewData);

        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listViewData.setAdapter(adapter);

        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        buttonRetrieveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });
    }

    private void addData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String name = editTextName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese un nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);

        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error al agregar datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos agregados correctamente", Toast.LENGTH_SHORT).show();
            editTextName.getText().clear();
        }
    }

    private void retrieveData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        dataList.clear();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            dataList.add("ID: " + id + ", Name: " + name);
        }
        cursor.close();

        if (dataList.size() > 0) {
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No hay datos en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }
}
