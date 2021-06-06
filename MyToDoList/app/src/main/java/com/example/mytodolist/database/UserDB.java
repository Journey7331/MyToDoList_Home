package com.example.mytodolist.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;

/**
 * @program: MyToDoList
 * @description: UserDB 类
 */
public class UserDB implements MyDatabaseHelper.TableCreateInterface {
    public static String TableName = "users";   // 表名
    // 字段名
    // phonenumber password name email birthday
    public static String _id = "_id";           // 主键
    public static String phone = "phone";   // 手机号
    public static String pwd = "password";      // 密码
    public static String name = "name";         // 用户的昵称
    public static String email = "email";       // 电子邮箱
    public static String birth = "birthday";    // 生日

    public static UserDB userDB = new UserDB();

    public static UserDB getInstance() {
        return UserDB.userDB;
    }

    public UserDB() {
    }

    // 创建数据表
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table if not exists " + UserDB.TableName + "("
                + BaseColumns._ID + " integer primary key autoincrement, "
                + UserDB.phone + " text,"
                + UserDB.pwd + " text,"
                + UserDB.name + " text,"
                + UserDB.email + " text,"
                + UserDB.birth + " text )";

        // 执行创建语句
        db.execSQL(sql);
        Log.i("create", "** User Table Created **");
    }

    // 更新数据库表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            String sql = "drop table if exists " + UserDB.TableName;
            db.execSQL(sql);
            this.onCreate(db);
        }
    }

    // 插入用户
    public static void insertUser(MyDatabaseHelper dbHelper, ContentValues userValues) {
        // 获得可写的数据库实例
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 插入用户
        db.insert(UserDB.TableName, null, userValues);
        Log.i("insert", "** insert user, phone number: " + userValues.get(UserDB.phone) + " **");

        db.close();
    }

    // 删除用户
    public static void deleteUser(MyDatabaseHelper dbHelper, int _id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 此处要求删除User._id为传入参数_id的对应记录,使游标指向此记录
        db.delete(
                UserDB.TableName,                  // 操作的表名
                UserDB._id + "=?",    // 删除的条件  wehere "_id" = _id
                new String[]{_id + ""}           // 条件中“？”对应的值
        );
        db.close();
    }

    // 修改用户 by phone
    public static void updateUser(MyDatabaseHelper dbHelper, String phoneNumber, ContentValues infoValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 根据phoneNumber修改对应的记录, 所要修改的内容保存在传入参数infoValues中
        db.update(
                UserDB.TableName,
                infoValues,
                UserDB.phone + " =? ",
                new String[]{phoneNumber + ""}
        );
        Log.i("update", "** user: " + phoneNumber + " updated **");
        db.close();
    }

    // 修改用户 by id
    public static void updateUser(MyDatabaseHelper dbHelper, int id, ContentValues infoValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 根据id修改对应的记录, 所要修改的内容保存在传入参数infoValues中
        db.update(
                UserDB.TableName,
                infoValues,
                UserDB._id + " =? ",
                new String[]{id + ""}
        );
        Log.i("update", "** userid: " + id + " updated **");
        db.close();
    }

    // 查询用户 by id
    public static HashMap<String, Object> getUser(MyDatabaseHelper dbHelper, int _id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap<String, Object> userMap = new HashMap<>();
        // 此处要求查询 User._id
        Cursor cursor = db.query(UserDB.TableName, null, UserDB._id + " =? ", new String[]{_id + ""}, null, null, null);
        // 移动到第一个结果上
        cursor.moveToFirst();
        // 获得每个字段的值
        userMap.put(UserDB.phone, cursor.getString(cursor.getColumnIndex(UserDB.phone)));
        userMap.put(UserDB.pwd, cursor.getString(cursor.getColumnIndex(UserDB.pwd)));
        userMap.put(UserDB.name, cursor.getString(cursor.getColumnIndex(UserDB.name)));
        userMap.put(UserDB.email, cursor.getString(cursor.getColumnIndex(UserDB.email)));
        userMap.put(UserDB.birth, cursor.getString(cursor.getColumnIndex(UserDB.birth)));

        // 如果需要遍历
//        for(cursor.moveToFirst(); cursor.isAfterLast(); cursor.moveToNext()){
//
//        }

        cursor.close();
        db.close();
        return userMap;
    }

    // 查询用户 by phone
    public static HashMap<String, Object> getUser(MyDatabaseHelper dbHelper, String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap<String, Object> userMap = new HashMap<>();
        // 此处要求查询 User.phone
        Cursor cursor = db.query(UserDB.TableName, null, UserDB.phone + " =? ", new String[]{phone + ""}, null, null, null);
        // 移动到第一个结果上
        cursor.moveToFirst();
        // 获得每个字段的值
        userMap.put(UserDB._id, cursor.getString(cursor.getColumnIndex(UserDB._id)));
        userMap.put(UserDB.phone, cursor.getString(cursor.getColumnIndex(UserDB.phone)));
        userMap.put(UserDB.pwd, cursor.getString(cursor.getColumnIndex(UserDB.pwd)));
        userMap.put(UserDB.name, cursor.getString(cursor.getColumnIndex(UserDB.name)));
        userMap.put(UserDB.email, cursor.getString(cursor.getColumnIndex(UserDB.email)));
        userMap.put(UserDB.birth, cursor.getString(cursor.getColumnIndex(UserDB.birth)));

        cursor.close();
        db.close();
        return userMap;
    }

    // 查重
    public static boolean checkPhoneExist(MyDatabaseHelper dbHelper, String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean ret = false;
        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " =? ",
                new String[]{phone + ""},
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {
            ret = true;
            Log.i("register", "** Phone Has Been Registered.");
        }

        cursor.close();
        db.close();
        return ret;
    }

    /*
     * return:
     *  1 all correct
     *  0 password wrong
     * -1 no this phone
     * */
    public static int login(MyDatabaseHelper dbHelper, String phone, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int ret;
        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " =? ",
                new String[]{phone + ""},
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            Log.i("Login", "** No This Phone.");
            ret = -1;
        } else {
            cursor.moveToFirst();
            String pwd = cursor.getString(cursor.getColumnIndex(UserDB.pwd));
            if (pwd.equals(password)) {
                Log.i("Login", "** All Correct. Log In Successfully.");
                ret = 1;
            } else {
                Log.i("Login", "** Password Wrong.");
                ret = 0;
            }
        }

        cursor.close();
        db.close();
        return ret;
    }

}
