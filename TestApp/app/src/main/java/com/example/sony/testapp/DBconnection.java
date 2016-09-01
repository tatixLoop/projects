package com.example.sony.testapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY on 01-08-2016.
 */
public class DBconnection extends SQLiteOpenHelper {
    SQLiteDatabase sqldb;
    public DBconnection(Context C)
    {
        super(C,"DB",null,1);
    }
    public void openConnection()
    {
        sqldb=getWritableDatabase();
    }

    public void closeConnection()
    {
        sqldb.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String qury="create table tb_details(did INTEGER PRIMARY KEY AUTOINCREMENT , dname VARCHAR(30) NOT NULL,mob VARCHAR(10) NOT NULL, email varchar(15) NOT NULL )";

        db.execSQL(qury);

        String q="create table tb_sum(no1 INTEGER  NOT NULL,no2 INTEGER NOT NULL,result INTEGER NOT NULL)";
        db.execSQL(q);


    }

    public boolean insertData(String qury)
    {
        try {
            sqldb.execSQL(qury);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public Cursor selectData(String qury)
    {
        Cursor cr=sqldb.rawQuery(qury,null);
        return cr;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
