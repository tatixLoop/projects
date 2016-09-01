package com.example.sony.samplenotificationservice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by SONY on 23-08-2016.
 */
public class DBConnection extends SQLiteOpenHelper {
    SQLiteDatabase sqldb;
    public DBConnection(Context C)
    {
        super(C,"DDb",null,1);
    }
    public void openConnection()
    {
        sqldb=getWritableDatabase();
        //openorcreatedatabase()
        //crete table and insert data
    }

    public void closeConnection()
    {
        sqldb.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

       // String qury="create table tb_details(did INTEGER PRIMARY KEY AUTOINCREMENT , dname VARCHAR(30) NOT NULL,mob VARCHAR(10) NOT NULL, email varchar(15) NOT NULL )";

        //db.execSQL(qury);

        String q="create table tb_time(Time varchar(10)  NOT NULL,Message VARCHAR(30) NOT NULL)";
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
