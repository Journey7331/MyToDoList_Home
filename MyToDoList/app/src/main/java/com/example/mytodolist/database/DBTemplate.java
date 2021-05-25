package com.example.mytodolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @program: MyToDoList
 * @description:
 */
public class DBTemplate {

    private MyDatabaseHelper dbHelper = null;

    public DBTemplate(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public boolean queryPhone(String phone) {
        return UserDB.checkPhoneExist(dbHelper, phone);
    }

    public void insertUser(ContentValues values) {
        UserDB.insertUser(dbHelper, values);
    }

    public int loginUser(String phone, String password) {
        return UserDB.login(dbHelper, phone, password);
    }
}
