package com.example.mytodolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
* version
* 1: Base Tables
* User:     _id | phone | password | name | email | birthday
* Event:    _id | content | done
*
* 2:
* Event:    Add memo | date | time
*
* 3:
* Event:    Add level
*
* 4:
* User:     Automatically authenticates logged accounts
*
* */


public class MyDatabaseHelper extends SQLiteOpenHelper {

    // 数据库名
    private static final String db_name = "test";
    private static final int version = 4;

    /**
     * 构造方法，创建数据库
     * context
     * name    数据库名
     * factory 游标类
     * version 数据库版本
     */
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, version);
    }

    /*
     * 创建接口
     * 实现各表的创建
     * */
    public static interface TableCreateInterface {
        public void onCreate(SQLiteDatabase db);

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    // 在创建数据库的时候onCreate会被调用，所以数据库创建之后，表会被创建且仅创建一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        EventDB.getInstance().onCreate(db);
        UserDB.getInstance().onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) {
            return;
        }

        EventDB.getInstance().onUpgrade(db, oldVersion, newVersion);
        UserDB.getInstance().onUpgrade(db, oldVersion, newVersion);

    }
}
