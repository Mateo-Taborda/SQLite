package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "carga_transportes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "transportes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "nombre";

    // Sentencia SQL para crear la tabla
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes implementar la lógica para actualizar la base de datos si la versión cambia.
    }
}
