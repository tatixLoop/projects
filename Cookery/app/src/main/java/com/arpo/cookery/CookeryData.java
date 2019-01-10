package com.arpo.cookery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Created by jithin on 10/1/19.
 */

public class CookeryData  extends SQLiteOpenHelper {
    SQLiteDatabase sqldb;
    Context mContext;

    public CookeryData (Context C) {
        super(C,"DB_Cookery",null,1);
        mContext = C;
    }

    public void openConnection()
    {
        sqldb =  mContext.openOrCreateDatabase("CookeryDB", Context.MODE_PRIVATE, null);
        createTables();
    }
    public void closeConnection()
    {
        sqldb.close();
    }

    private void createTables()
    {

        String qury = "CREATE TABLE  IF NOT EXISTS  tbl_dishes(id INTEGER NOT NULL,"+
                "type INTEGER NOT NULL,"+
                "dishname varchar(56) NOT NULL,"+
                "img_path varchar(56) NOT NULL,"+
                "calory INTEGER NOT NULL,"+
                "cooktimeinsec INTEGER NOT NULL,"+
                "serves INTEGER NOT NULL,"+
                "rating INTEGER NOT NULL,"+
                "author varchar(64) NOT NULL,"+
                "numRating INTEGER NOT NULL)";
        sqldb.execSQL(qury);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
    }

    public void getDishList(List <ListItemDishes> list)
    {
        String query = "SELECT * FROM tbl_dishes";
        Cursor data = sqldb.rawQuery(query, null);

        while (data.moveToNext())
        {
            ListItemDishes dish = new ListItemDishes(data.getInt(0),
                    data.getInt(1),
                    data.getString(2),
                    data.getString(3),
                    data.getInt(4),
                    data.getInt(5),
                    data.getInt(6),
                    data.getInt(7),
                    data.getInt(9),
                    data.getString(8)
                    );
            list.add(dish);
        }
    }

    public int getLastId()
    {
        int lastId = 0;

        String query = "SELECT max(id) FROM tbl_dishes";
        Cursor checkData = sqldb.rawQuery(query, null);
        while (checkData.moveToNext())
        {
            lastId = checkData.getInt(0);
        }
        return lastId;
    }

    public void syncDB(List<ListItemDishes> list)
    {
        for ( ListItemDishes dish:
              list) {
            String name = dish.getName().replace("'","''");
            String author = dish.getAuthor().replace("'","''");
            String query="INSERT INTO tbl_dishes (id, type, dishname, img_path, calory, cooktimeinsec, serves, author, rating, numRating) VALUES ("+
                    dish.getId() +", "+
                    dish.getType() + ", '"+
                    name +"','"+
                    dish.getImg_path() +"',"+
                    dish.getCalory() +","+
                    dish.getCooktimeinsec() +","+
                    dish.getServeCount() +",'"+
                    author + "',"+
                    dish.getRating() + ","+
                    dish.getNumRating()+
                    ")";
            sqldb.execSQL(query);
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void print(String str)
    {
        Log.d("JKS",str);
    }

}