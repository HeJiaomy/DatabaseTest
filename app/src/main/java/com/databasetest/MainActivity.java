package com.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    private Button create_db;
    private Button add_btn;
    private Button update_btn;
    private Button delete_btn;
    private Button query_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        create_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getReadableDatabase();
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装第一条数据
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);  //插入第一条数据
                values.clear();
                //开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);

                String str= "insert into Book (name,author,pages,price) values(?,?,?,?)";
                db.execSQL(str,new String[]{"The Da Vinci Code","Dan Brown","454","16.96"});
                //使用SQL语句
//                db.execSQL("insert into Book (name,author,pages,price) values(?,?,?,?)", new String[]{"The Lost Symbol", "Dan Brown", "510", "19.95"});
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name=?", new String[]{"The Da Vinci Code"});
                //使用SQL语句
//                db.execSQL("update Book set price=? where name=?", new String[]{"10.99", "The Da Vinci Code"});
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"500"});
                //使用SQL语句
//                db.execSQL("delete from Book where pages>?", new String[]{"500"});
            }
        });

        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                //使用SQL语句
//                Cursor cursor = db.rawQuery("select * from Book", null);
                Cursor cursor= db.query("Book",null,null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        //遍历Cursor对象，取出数据并打印
                        String name= cursor.getString(cursor.getColumnIndex("name"));
                        String author= cursor.getString(cursor.getColumnIndex("author"));
                        int pages= cursor.getInt(cursor.getColumnIndex("pages"));
                        int price= cursor.getInt(cursor.getColumnIndex("price"));

                        Log.e("MainActivity","book name is:"+name);
                        Log.e("MainActivity","book author is:"+author);
                        Log.e("MainActivity","book pages is:"+pages);
                        Log.e("MainActivity","book price is:"+price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }

    private void init() {
        create_db = findViewById(R.id.create_database);
        add_btn = findViewById(R.id.add_btn);
        update_btn = findViewById(R.id.update_btn);
        delete_btn = findViewById(R.id.delete_btn);
        query_btn = findViewById(R.id.query_btn);
    }
}
