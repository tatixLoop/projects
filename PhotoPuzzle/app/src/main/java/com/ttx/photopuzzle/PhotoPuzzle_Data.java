package com.ttx.photopuzzle;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SONY on 19-06-2017.
 */

public class PhotoPuzzle_Data extends SQLiteOpenHelper {



        SQLiteDatabase sqldb;
        Context mContext;


        private void print(String str)
        {
            Log.d("JKS",str);
        }

        public PhotoPuzzle_Data(Context C)
        {
            super(C,"DB_PhotoPuzzzle",null,1);
            mContext = C;
        }
        public void openConnection()
        {
            sqldb =  mContext.openOrCreateDatabase("PhotoPuzzzle_DB", Context.MODE_PRIVATE, null);
            createTables();
            initData();
        }

    private void createTables()
    {

        String qury = "CREATE TABLE  IF NOT EXISTS  tb_photopuzzle(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, puzzleno INTEGER NOT NULL ,status INTEGER NOT NULL ,timetaken INTEGER NOT NULL,userid INTEGER NOT NULL,puzzletype INTEGER NOT NULL,puzzleimg INTEGER NOT NULL)";
        sqldb.execSQL(qury);
    }


    private void initData() {

        String checkQuery = "SELECT * FROM tb_photopuzzle";
        Cursor checkData = sqldb.rawQuery(checkQuery, null);
        if (checkData.getCount() != 0) {
            return;
        }


        ///// insert data here

        int puzzleimg=0;
        for (int i=2;i<6;i++)
        {
            for (int puzzleno=1;puzzleno<14;puzzleno++)
            {
                String insertion="insert into tb_photopuzzle(puzzleno,status,timetaken,userid,puzzletype,puzzleimg)values("+puzzleno+",0,0,0,"+i+","+puzzleimg+")";
                print(insertion);
                sqldb.execSQL(insertion);

                puzzleimg++;
            }
        }

    }
    public void closeConnection()
    {
        sqldb.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {


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
