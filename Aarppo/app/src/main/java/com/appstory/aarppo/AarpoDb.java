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



        String qury = "create table tbl_teamName(teamName VARCHAR(50) NOT NULL, teamId INTEGER NOT NULL, homeGround VARCHAR(200) NOT NULL)";
        db.execSQL(qury);

        String qury1 = "create table tbl_schedule(sched_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                    " date_time DATETIME NOT NULL , "+
                                                    " timeZone  VARCHAR(5) NOT NULL ,"+
                                                    " team1 INTEGER NOT NULL ,"+
                                                    " team2 INTEGER NOT NULL," +
                                                    " result INTEGER )";
        db.execSQL(qury1);

        qury = "create table tbl_AARPO(sched_id INTEGER, "+
                " aarpo1 INTEGER NOT NULL ,"+
                " aarpo2 INTEGER NOT NULL," +
                " aarpo3 INTEGER NOT NULL," +
                " aarpo4 INTEGER NOT NULL," +
                " aarpo5 INTEGER NOT NULL," +
                " aarpo6 INTEGER NOT NULL," +
                " aarpo7 INTEGER NOT NULL," +
                " aarpo8 INTEGER NOT NULL)";

        db.execSQL(qury);


        String query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('North East United FC',1, 'Indira Gandhi Athletic Stadium, Guwahati')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('Kerala Blasters FC',2,'Jawaharlal Nehru Stadium, Kochi')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('Atlético de Kolkata',3, 'Rabindra Sarobar Stadium, Kolkata')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('FC Pune City ',4,'Balewadi Stadium, Pune')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('Mumbai City FC',5,'Mumbai Football Arena, Mumbai')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('Chennaiyin FC',6,'Jawaharlal Nehru Stadium, Chennai')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('FC Goa ',7,'Jawaharlal Nehru Stadium, Fatorda')";
        db.execSQL(query);
        query = "INSERT INTO tbl_teamName (teamName,teamId,homeGround) values('Delhi Dynamos FC',8,'Jawaharlal Nehru Stadium, Delhi')";
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
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-05 19:00:00','IST', 2,3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-06 19:00:00','IST', 6,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-07 19:00:00','IST', 5,1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-08 19:00:00','IST', 7,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-09 19:00:00','IST', 2,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-11 19:00:00','IST', 5,3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-12 19:00:00','IST', 4,1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-13 19:00:00','IST', 6,7)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-14 19:00:00','IST', 2,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-15 19:00:00','IST', 8,1 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-16 19:00:00','IST', 3,7)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-17 19:00:00','IST', 4,2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-18 19:00:00','IST', 8,5)";
        db.execSQL(query);

        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-20 19:00:00','IST',1,6 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-21 19:00:00','IST',5,7 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-22 19:00:00','IST',3,8 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-23 19:00:00','IST',4,6 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-24 19:00:00','IST',7,2 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-25 19:00:00','IST', 3,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-27 19:00:00','IST', 8,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-28 19:00:00','IST', 1,3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-29 19:00:00','IST', 6,2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-10-30 19:00:00','IST', 7,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-02 19:00:00','IST', 6,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-03 19:00:00','IST', 4,7)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-04 19:00:00','IST', 8,2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-05 19:00:00','IST', 1,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-06 19:00:00','IST', 4,3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-08 19:00:00','IST', 2,7)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-09 19:00:00','IST', 8,6)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-10 19:00:00','IST', 5,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-11 19:00:00','IST', 7,1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-12 19:00:00','IST', 2,6)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-13 19:00:00','IST', 8,3)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-15 19:00:00','IST', 6,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-16 19:00:00','IST', 7,5)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-17 19:00:00','IST', 3,1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-18 19:00:00','IST', 4,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-19 19:00:00','IST', 5,2)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-20 19:00:00','IST',6,3 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-22 19:00:00','IST',1,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-23 19:00:00','IST',5,6 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-24 19:00:00','IST',7,3 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-25 19:00:00','IST',2,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-26 19:00:00','IST', 6,1)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-27 19:00:00','IST',8,7 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-29 19:00:00','IST',3,2 )";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-11-30 19:00:00','IST',1,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-12-01 19:00:00','IST',7,6)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-12-01 19:00:00','IST',3,4)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-12-01 19:00:00','IST',5,8)";
        db.execSQL(query);
        query = "INSERT INTO tbl_schedule (date_time, timeZone, team1, team2) "+
                " values('2016-12-01 19:00:00','IST',2,1)";
        db.execSQL(query);


        query = "SELECT sched_id FROM tbl_schedule";
        Cursor c = db.rawQuery(query,null);
        for(int i = 0; i < c.getCount();i++) {
            qury = "INSERT INTO tbl_AARPO (sched_id,aarpo1,aarpo2,aarpo3,aarpo4,aarpo5,aarpo6,aarpo7,aarpo8) values("+i+",1,1,1,1,1,1,1,1)";
            db.execSQL(qury);
        }

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

