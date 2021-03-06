package com.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by 12191 on 2017/12/11.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {


    public static final String CREATE_BOOK="create table Book(id integer primary key autoincrement,author text,price real,pages integer,name text)";

    public static final String CREATE_CATEGROY="create table Category(id integer primary key autoincrement,category_name text,category_code integer)";

    private Context context;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGROY);
//        Toast.makeText(context,"Create Succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }
}
