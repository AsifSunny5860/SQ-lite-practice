package com.example.mysqlll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;


public class DatabaseeHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Expense.db";
    public static final String TABLE_NAME ="Expense";
    public static final int VERSION =1;

    public static final String COL_ID = "ID";
    public static final String COL_TYPE = "Type";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_DATE ="Date";
    public static final String COL_TIME="Time";
   // public static final String COL_IMAGE="Image";

    String create_table = "create table " + TABLE_NAME + " (ID integer primary key, Type text, Amount Text,Date Integer,Time Text)";


    public DatabaseeHelper( Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override


    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertdata(String name, String amount, long date, String time){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TYPE,name);
        contentValues.put(COL_AMOUNT,amount);
        contentValues.put(COL_DATE,date);
        contentValues.put(COL_TIME,time);
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        long id =sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return id;
    }

    public Cursor showData() {
        String show_all = "select * from " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(show_all, null);
        return cursor;
    }

    public void deleteData(int id) {
        getWritableDatabase().delete(TABLE_NAME," ID =?",new String[]{String.valueOf(id)});
    }
}
