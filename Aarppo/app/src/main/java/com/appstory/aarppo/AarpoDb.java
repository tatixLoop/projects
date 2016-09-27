package com.appstory.aarppo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jithin suresh on 27-09-2016.
 */
public class AarpoDb  extends SQLiteOpenHelper{

    SQLiteDatabase sqldb;
    Context c;



    public AarpoDb()
    {
        super(null, null, null, 1);
    }


    public AarpoDb(Context C)
    {
        super(C,"DB_pushup",null,1);
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



        String qury = "create table tbl_teamName(teamName VARCHAR(50) NOT NULL, teamId INTEGER NOT NULL)";
        db.execSQL(qury);

        String qury1 = "create table tbl_schedule(sched_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                    " date_time DATETIME NOT NULL , "+
                                                    " timeZone  VARCHAR(5) NOT NULL ,"+
                                                    " team1 INTEGER NOT NULL ,"+
                                                    " team2 INTEGER NOT NULL," +
                                                    " result INTEGER )";
        db.execSQL(qury1);

        String query = "INSERT INTO tbl_teamName (teamName,teamId) values('North East United FC',1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('Kerala Blasters FC',2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('Atlético de Kolkata',3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('FC Pune City ',4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('Mumbai City FC',5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('Chennaiyin FC',6)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('FC Goa ',7)";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId) values('Delhi Dynamos FC',8)";
        db.execSQL(query);


        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-01 19:00:00','IST', 1,2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-02 19:00:00','IST', 3,6)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-03 19:00:00','IST', 4,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-04 19:00:00','IST', 1,7)";
        db.execSQL(query);


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

