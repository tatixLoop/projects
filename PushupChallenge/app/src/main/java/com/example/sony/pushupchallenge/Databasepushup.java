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

        String qury="create table pushupdata(Data_id INTEGER PRIMARY KEY AUTOINCREMENT ,Pushup_id INTEGER PRIMARY KEY AUTOINCREMENT , Data VARCHAR(500) NOT NULL ,Attemptno VARCHAR(30) NOTNULL , )";
        db.execSQL(qury);
        //Toast.makeText(c, "created", Toast.LENGTH_SHORT).show();
        String ins="insert into tb_excersise(excersisetype)values('STANDARD')";
        db.execSQL(ins);
        String ins1="insert into tb_excersise(excersisetype)values('INTERMEDIATE')";
        db.execSQL(ins1);

        // Toast.makeText(c, "inserted", Toast.LENGTH_SHORT).show();
        String ins2="insert into tb_excersise(excersisetype)values('ADVANCED')";
        db.execSQL(ins2);
        // Toast.makeText(c, "inserted", Toast.LENGTH_SHORT).show();



        String q = "create table tb_food(fid integer PRIMARY KEY autoincrement, fname varchar(30), fprice varchar(10), fdes varchar(100),ftype_id integer)";
        db.execSQL(q);
        String[] names={"Rice", "Chappathi","Dosa", "Idly"},p={"100","20","30","15"},d={"good","very good","xdsdsd","sssss"};
        String[] n1={"Noodles", "Soop","Porotta","Lays"},p1={"60","100","25","30"},d1={"very nice","tasty","sjgfjsf","jsusy shjdus shs"};
        for(int i=0;i<names.length;i++){
            String ins3="insert into tb_food(fname, fprice, fdes,ftype_id)values('"+names[i]+"','"+p[i]+"','"+d[i]+"',1)";
            db.execSQL(ins3);
        }
        for (int i=0;i<n1.length;i++)
        {
            String ins3="insert into tb_food(fname,fprice,fdes,ftype_id)values('"+n1[i]+"','"+p1[i]+"','"+d1[i]+"',2)";
            db.execSQL(ins3);
        }
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
