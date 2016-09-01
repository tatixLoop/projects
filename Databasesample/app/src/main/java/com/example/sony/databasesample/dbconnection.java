package com.example.sony.databasesample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by SONY on 31-08-2016.
 */
public class dbconnection extends SQLiteOpenHelper{

    SQLiteDatabase sqldb;
    Context c;
    public dbconnection(Context C)
    {
        super(C,"DB",null,1);
        c=C;
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

        String qury="create table tb_food(food_id INTEGER PRIMARY KEY AUTOINCREMENT , foodname VARCHAR(30) NOT NULL,foodtype VARCHAR(10) NOT NULL)";

        db.execSQL(qury);
        String ins="insert into tb_food(foodname, foodtype)values('Biriyani', 'INDIAN')";
        db.execSQL(ins);
        Toast.makeText(c, "inserted", Toast.LENGTH_SHORT).show();
        //String q="create table tb_sum(no1 INTEGER  NOT NULL,no2 INTEGER NOT NULL,result INTEGER NOT NULL)";
       // db.execSQL(q);


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
