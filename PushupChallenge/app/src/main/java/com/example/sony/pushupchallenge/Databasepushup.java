package com.example.sony.pushupchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by SONY on 06-09-2016.
 */
public class Databasepushup extends SQLiteOpenHelper {

    SQLiteDatabase sqldb;
    Context c;






    public Databasepushup(Context C)
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

        String qury = "create table staminatest(Testno INTEGER PRIMARY KEY NOT NULL, Data VARCHAR(500) NOT NULL , pushupno INTEGER NOT NULL ,Timetaken INTEGER NOT NULL ,Score INTEGER NOT NULL)";
        db.execSQL(qury);

        String qury1 = "create table userdata(Userid INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(30) NOT NULL , height INTEGER NOT NULL ,weight INTEGER NOT NULL ,Score INTEGER NOT NULL)";
        db.execSQL(qury1);


        String qury2 = "create table pushupdata(Data_id INTEGER PRIMARY KEY AUTOINCREMENT ,Pushup_id INTEGER NOT NULL , Data VARCHAR(500) NOT NULL ,Attemptno VARCHAR(30) NOT NULL ,Numpushup INTEGER NOT NULL,Timetaken INTEGER NOT NULL,Score INTEGER NOT NULL,Badge INTEGER NOT NULL )";
        db.execSQL(qury2);


        String qury3 = "create table tb_pushupdetails(Pushup_id INTEGER PRIMARY KEY  , Name VARCHAR(500) ,Description VARCHAR(500)  ,Imageid INTEGER  ,locked INTEGER ,Badge INTEGER  ,Targetnopushup INTEGER )";
        db.execSQL(qury3);


        String qury4 = "create table pushupsteps(Pushup_id INTEGER PRIMARY KEY NOT NULL, Step1 VARCHAR(500) NOT NULL , Step2 VARCHAR(500) NOT NULL ,STEP3 VARCHAR(500),STEP4 VRCHAR(500) )";
        db.execSQL(qury4);

        Toast.makeText(c, "created", Toast.LENGTH_SHORT).show();

                /// insertion

        String ins="insert into tb_pushupdetails(excersisename)values('STANDARD')";

        db.execSQL(ins);
        String ins1="insert into tb_pushupdetails(excersisename)values('KNEE PUSHUP')";

        db.execSQL(ins1);
        String ins2="insert into tb_pushupdetails(excersisename)values('SHOULDER TAP')";

        db.execSQL(ins2);
        String ins3="insert into tb_pushupdetails(excersisename)values('HAND TAP')";

        db.execSQL(ins3);
        String ins4="insert into tb_pushupdetails(excersisename)values('T PUSHUP')";

        db.execSQL(ins4);
        String ins5="insert into tb_pushupdetails(excersisename)values('TIGHT TAP ')";

        db.execSQL(ins5);
        String ins6="insert into tb_pushupdetails(excersisename)values('SINGLE HAND RAISED')";

        db.execSQL(ins6);
        String ins7="insert into tb_pushupdetails(excersisename)values('SINGLE LEG RAISED')";

        db.execSQL(ins7);
        String ins8="insert into tb_pushupdetails(excersisename)values('KNUCKLE PUSHUP')";

        db.execSQL(ins8);
        String ins9="insert into tb_pushupdetails(excersisename)values('STAGGERED PUSHUP')";

        db.execSQL(ins9);
        String ins10="insert into tb_pushupdetails(excersisename)values('ALLIGATOR PUSHUP')";

        db.execSQL(ins10);
        String ins11="insert into tb_pushupdetails(excersisename)values('SLOW TO KNEE')";

        db.execSQL(ins11);
        String ins12="insert into tb_pushupdetails(excersisename)values('SPIDERMAN PUSHUP')";

        db.execSQL(ins12);
        String ins13="insert into tb_pushupdetails(excersisename)values('KNEE TO CHEST')";

        db.execSQL(ins13);
        String ins14="insert into tb_pushupdetails(excersisename)values('PSEUDO PLAINCHEE PUSHUP')";

        db.execSQL(ins14);
        String ins15="insert into tb_pushupdetails(excersisename)values('OUTSIDE LEG KICK')";

        db.execSQL(ins15);
        String ins16="insert into tb_pushupdetails(excersisename)values('GRASSHOPPER PUSHUP')";

        db.execSQL(ins16);
        String ins17="insert into tb_pushupdetails(excersisename)values('FOOT TAP')";

        db.execSQL(ins17);
        String ins18="insert into tb_pushupdetails(excersisename)values('KNEE TO OPPOSITE ')";

        db.execSQL(ins18);
        String ins19="insert into tb_pushupdetails(excersisename)values('CROSS SCREW PUSHUP ELBOW')";

        db.execSQL(ins19);
        String ins20="insert into tb_pushupdetails(excersisename)values('DIAMOND PUSHUP')";

        db.execSQL(ins20);
        String ins21="insert into tb_pushupdetails(excersisename)values('WIDE PUSHUP')";

        db.execSQL(ins21);
        String ins22="insert into tb_pushupdetails(excersisename)values('TIGER PUSHUP')";

        db.execSQL(ins22);
        String ins23="insert into tb_pushupdetails(excersisename)values('PIKE PUSHUP')";

        db.execSQL(ins23);
        String ins24="insert into tb_pushupdetails(excersisename)values('FEET ELIVATED PUSHUP')";

        db.execSQL(ins24);
        String ins25="insert into tb_pushupdetails(excersisename)values('SIDE ROLL PUSHUP')";

        db.execSQL(ins25);
        String ins26="insert into tb_pushupdetails(excersisename)values('JACK KNIFE PUSHUP')";

        db.execSQL(ins26);
        String ins27="insert into tb_pushupdetails(excersisename)values('YOGA PUSHUP')";

        db.execSQL(ins27);
        String ins28="insert into tb_pushupdetails(excersisename)values('EXPLOSIVE STAGGERED PUSHUP')";

        db.execSQL(ins28);
        String ins29="insert into tb_pushupdetails(excersisename)values('EXPLOSIVE JACKS PUSHUP')";

        db.execSQL(ins29);
        String ins30="insert into tb_pushupdetails(excersisename)values('CLAP PUSHUP')";

        db.execSQL(ins30);
        String ins31="insert into tb_pushupdetails(excersisename)values('SLIDER PUSHUP')";

        db.execSQL(ins31);
        String ins32="insert into tb_pushupdetails(excersisename)values('FEET ON WALL')";

        db.execSQL(ins32);
        String ins33="insert into tb_pushupdetails(excersisename)values('SUPERMAN PUSHUP')";

        db.execSQL(ins33);
        String ins34="insert into tb_pushupdetails(excersisename)values('FINGER TIP PUSHUP')";

        db.execSQL(ins34);





        Toast.makeText(c, "inserted", Toast.LENGTH_SHORT).show();


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
