package com.example.mytodolist.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.mytodolist.entity.User;

import java.util.HashMap;
import java.util.Objects;

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

        // 插入 记录 当前登录用户的phone 的数据条
        // insert an data which tells who's using the account
        ContentValues admin = new ContentValues();
        admin.put(BaseColumns._ID, 0);
        admin.put(UserDB.phone, "");
        admin.put(UserDB.name, "");
        db.insert(UserDB.TableName, null, admin);

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

    // 查询用户 by phone
    public static User getUser(MyDatabaseHelper dbHelper, String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 此处要求查询 User.phone
        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " = ? AND " + UserDB._id + " != ? ",
                new String[]{phone, 0 + ""},
                null, null, null);

        // 移到第一个
        cursor.moveToFirst();

        User user = new User();
        user.set_id(cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)));
        user.setPhone(cursor.getString(cursor.getColumnIndex(UserDB.phone)));
        user.setName(cursor.getString(cursor.getColumnIndex(UserDB.name)));
        user.setPwd(cursor.getString(cursor.getColumnIndex(UserDB.pwd)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(UserDB.email)));
        user.setBirth(cursor.getString(cursor.getColumnIndex(UserDB.birth)));

        cursor.close();
        db.close();

        return user;
    }

    public static String getPhone(MyDatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB._id + " = ? ",
                new String[]{ 0+"" },
                null, null, null);
        cursor.moveToFirst();
        String phoneGet = "";
        phoneGet = cursor.getString(cursor.getColumnIndex(UserDB.phone));

        cursor.close();
        db.close();
        return phoneGet;
    }

    public static String getName(MyDatabaseHelper dbHelper, String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " = ? AND " + UserDB._id + " != ? ",
                new String[]{phone, 0 + ""},
                null, null, null);

        String nameGet = "Stranger!";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            nameGet = cursor.getString(cursor.getColumnIndex(UserDB.name));
        }

        cursor.close();
        db.close();
        return nameGet;
    }

    // 查重
    public static boolean checkPhoneExist(MyDatabaseHelper dbHelper, String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean ret = false;
        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " = ? ",
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
        int ret = -2;
        Cursor cursor = db.query(
                UserDB.TableName,
                null,
                UserDB.phone + " = ? AND " + UserDB._id + " != ? ",
                new String[]{phone + "", 0 + ""},
                null,
                null,
                null
        );
        if (cursor.getCount() == 0) {
            Log.i("Login", "** No This Phone.");
            ret = -1;
        } else {
            cursor.moveToFirst();
            int idGet = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
            if (idGet != 0) {
                String pwd = cursor.getString(cursor.getColumnIndex(UserDB.pwd));
                if (pwd.equals(password)) {
                    Log.i("Login", "** All Correct. Log In Successfully.");
                    ret = 1;
                } else {
                    Log.i("Login", "** Password Wrong.");
                    ret = 0;
                }
            }
        }

        cursor.close();
        db.close();
        return ret;
    }


}
