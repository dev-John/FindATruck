package com.project.findatruck.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "foodtruckloc.db";
    private static final int DATABASE_VERSION = 1; //2 para a 2 versao, criando nova coluna
    private final String CREATE_TABLE =
            "CREATE TABLE " +
                    "localizacoes " +
                    "(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "latitude TEXT NOT NULL, " +
                        "longitude TEXT NOT NULL, " +
                        "usuario_cadastrante TEXT NOT NULL, " +
                        "endereco TEXT" +
                    ");";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
