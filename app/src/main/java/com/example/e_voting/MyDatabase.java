package com.example.e_voting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase {

    // name of our database
    private final String DATABASE_NAME = "eVotingDB";


    // tables used in this database
    private final String DATABASE_TABLE_LOGIN = "Login_Table";


    // login table to control the number of users
    private final String KEY_ID_LOGIN = "_id";
    private final String KEY_FIRSTNAME_LOGIN = "_firstname";
    private final String KEY_LASTNAME_LOGIN = "_lastname";
    private final String KEY_MAIL_LOGIN = "_mail";
    private final String KEY_GENDER_LOGIN = "_gender";
    private final String KEY_PASSWORD_LOGIN = "_password";
    private final String KEY_ISLOGIN = "_islogin";


    // the current version of our database
    private final int DATABASE_VERSION = 1;

    Context context;

    MyDatabaseHelper helper;
    SQLiteDatabase sqLiteDatabase;

    public MyDatabase(Context c)
    {
        context = c;
    }


    // connection of our database
    public void open() {
        helper = new MyDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = helper.getWritableDatabase();
    }
    public void close() {
        sqLiteDatabase.close();
        helper.close();
    }


    //----------------------------------------------------------------------------------------------------
    // functions we needed to control the users in the database

    public void insertLogin(String firstName, String lastName, String email, String password, String gender) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_FIRSTNAME_LOGIN, firstName);
        cv.put(KEY_LASTNAME_LOGIN, lastName);
        cv.put(KEY_MAIL_LOGIN, email);
        cv.put(KEY_PASSWORD_LOGIN, password);
        cv.put(KEY_GENDER_LOGIN, gender);
        cv.put(KEY_ISLOGIN, 0);

        long temp = sqLiteDatabase.insert(DATABASE_TABLE_LOGIN, null, cv);
        if(temp == -1)
        {
            Toast.makeText(context, "Not Registered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Registered", Toast.LENGTH_SHORT).show();
        }

    }


    public int loginUser(String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ISLOGIN, 1);
        return sqLiteDatabase.update(DATABASE_TABLE_LOGIN, cv, KEY_MAIL_LOGIN+ "=? and "+KEY_PASSWORD_LOGIN+ "=?", new String[]{email,password});
    }
    public void logoutUser(){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ISLOGIN, 0);

        sqLiteDatabase.update(DATABASE_TABLE_LOGIN, cv, null, new String[]{});


    }

    public boolean isUserLoggedIn() {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE_LOGIN + " WHERE " + KEY_ISLOGIN + " = 1", null);
        boolean isLoggedIn = cursor.getCount() > 0;
        cursor.close();
        return isLoggedIn;
    }

    public String getLoggedInUserName() {
        String firstName = null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + KEY_FIRSTNAME_LOGIN + " FROM " + DATABASE_TABLE_LOGIN + " WHERE " + KEY_ISLOGIN + " = 1", null);
        if (cursor.moveToFirst()) {
            int id =cursor.getColumnIndex(KEY_FIRSTNAME_LOGIN);
            firstName = cursor.getString(id);
        }
        cursor.close();
        return firstName;
    }
    public int getLoggedInID(){
        int id = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + KEY_ID_LOGIN + " FROM " + DATABASE_TABLE_LOGIN + " WHERE " + KEY_ISLOGIN + " = 1", null);
        if (cursor.moveToFirst()) {
            int temp =cursor.getColumnIndex(KEY_ID_LOGIN);
            id = cursor.getInt(temp);
        }
        cursor.close();
        return id;
    }




    //----------------------------------------------------------------------------------------------------
    // helper class of our database

    private class MyDatabaseHelper extends SQLiteOpenHelper
    {
        public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // LOGIN TABLE CREATION
            db.execSQL("CREATE TABLE "+DATABASE_TABLE_LOGIN+"("+
                    KEY_ID_LOGIN+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_FIRSTNAME_LOGIN+" TEXT NOT NULL, "+
                    KEY_LASTNAME_LOGIN+" TEXT NOT NULL, "+
                    KEY_MAIL_LOGIN+" TEXT NOT NULL, "+
                    KEY_GENDER_LOGIN+" TEXT NOT NULL, "+
                    KEY_ISLOGIN+" INTEGER NOT NULL, "+
                    KEY_PASSWORD_LOGIN+" TEXT NOT NULL);");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if ever want to backup data, write code here

            db.execSQL("DROP TABLE "+DATABASE_TABLE_LOGIN+" IF EXISTS");
            onCreate(db);
        }
    }
}
