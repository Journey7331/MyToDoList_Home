package com.example.mytodolist.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * @program: MyToDoList
 * @description: EventDB
 */
public class EventDB implements MyDatabaseHelper.TableCreateInterface {
// 表名
    public static String TableName = "events";
    // 字段名
    public static String _id = "_id";           // 主键
    public static String content = "content";   // 内容
    public static String done = "done";         // 是否完成
    public static String date = "date";         // ddl
//    public static String type = "type";         // 类型
//    public static String color = "color";       // 颜色

    public static EventDB eventDB = new EventDB();

    public static EventDB getInstance() {
        return eventDB;
    }

    public EventDB() {

    }

    // 创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + EventDB.TableName + "("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + EventDB.content + " text,"
                + EventDB.done + " boolean,"
                + EventDB.date + " text"
                + ")";

        db.execSQL(sql);
        Log.i("create", "** Event Table Created **");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            String sql = "drop table if exists " + EventDB.TableName;
            db.execSQL(sql);
            this.onCreate(db);
        }
    }

    // 插入Event
    public static void insertEvent(MyDatabaseHelper dbHelper, ContentValues eventValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(EventDB.TableName, null, eventValues);
        String content = eventValues.get(EventDB.content).toString();
        Log.i("insert", "** insert an event: " + content + " **");
    }


    // 删除Event By Id
    public static void deleteEventById(MyDatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(EventDB.TableName, BaseColumns._ID + "=?", new String[]{id + ""});
        db.close();
    }

    // 

}
