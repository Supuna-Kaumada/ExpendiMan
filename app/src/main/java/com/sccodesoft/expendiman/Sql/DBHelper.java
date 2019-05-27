package com.sccodesoft.expendiman.Sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fatima on 11/04/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ExpMan.db";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE Users" +
            "(User_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name TEXT," +
            "Password TEXT," +
            "Income TEXT," +
            "Expenditure TEXT," +
            "Email TEXT )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS Users";

    private static final String SQL_CREATE_Logs = "CREATE TABLE Logs" +
            "(Log_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name TEXT," +
            "Amount TEXT," +
            "user_Email TEXT," +
            "Type TEXT )";

    private static final String SQL_DELETE_Logs = "DROP TABLE IF EXISTS Logs";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_Logs);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_Logs);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}