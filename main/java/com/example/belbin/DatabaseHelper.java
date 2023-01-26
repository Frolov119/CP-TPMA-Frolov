package com.example.belbin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "BelbinTestDB.db", null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table user(" +
                "id integer primary key autoincrement," +
                "login text," +
                "password text," +
                "role integer);");
        sqLiteDatabase.execSQL("create table test(" +
                "id_test integer primary key autoincrement," +
                "id_user integer," +
                "res_i integer," +
                "res_p integer," +
                "res_f integer," +
                "res_m integer," +
                "res_r integer," +
                "res_o integer," +
                "res_k integer," +
                "res_d integer);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL("drop table if exists test");
    }
    public boolean insertInTest(Integer user, Integer[] mass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id_user", user);
        contentValues.put("res_i", mass[0]);
        contentValues.put("res_p", mass[1]);
        contentValues.put("res_f", mass[2]);
        contentValues.put("res_m", mass[3]);
        contentValues.put("res_r", mass[4]);
        contentValues.put("res_o", mass[5]);
        contentValues.put("res_k", mass[6]);
        contentValues.put("res_d", mass[7]);
        long result = db.insert("test", null, contentValues);
        if (result == -1) {
            return  false;
        } else {
            return true;
        }
    }
    public boolean insertInUser(String name, String pass, Integer rule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("login", name);
        contentValues.put("password", pass);
        contentValues.put("role", rule);
        long result = db.insert("user", null, contentValues);
        if (result == -1) {
            return  false;
        } else {
            return true;
        }
    }
    public boolean checkLogin(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where login=?", new String[]{login});
        if (cursor.getCount() > 0) {
            return false;
        } else return true;
    }
    public boolean chkLoginPass (String login, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where login=? and password=?", new String[]{login,pass});
        if (cursor.getCount() > 0) {
            return true;
        } else return false;
    }
    public int getIdUser(String login, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where login=? and password=?", new String[]{login,pass});
        cursor.moveToFirst();
        int idColumn = cursor.getColumnIndex("id");
        int idUser = cursor.getInt(idColumn);
        return idUser;
    }
    public int getIdLastTest(int user) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from test where id_user = " + user;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        int idColumn = cursor.getColumnIndex("id_test");
        int idTest = cursor.getInt(idColumn);
        return idTest;
    }
    public Integer[] getTest(int test) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from test where id_test = " + test;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Integer[] mass = new Integer[8];
        for (int i = 0; i < 8; i++) {
            mass[i] = cursor.getInt(i + 2);
        }
        return mass;
    }
}
