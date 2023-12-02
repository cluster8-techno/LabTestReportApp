package com.onlinelabreport.onlinelabreport;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DBName ="register.db";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DBName, null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(useremail TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists users");
    }


    //insert data to user database
    public boolean insertData(String useremail, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("useremail", useremail);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);
        return result != -1;
    }

    //checks whether a given user email exists in a SQLite database
    public boolean checkUserEmail(String useremail) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where useremail = ?", new String[]{useremail});
        return cursor.getCount() > 0;
    }

    //checks whether a given user  exists in a SQLite database
    public boolean checkUser(String useremail, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where useremail =? and password =?", new String[]{useremail, password});
        return cursor.getCount() > 0;
    }
}

