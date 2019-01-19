package com.arpo.cookery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
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

        //Create table where favorites dishes are kept
        qury = "CREATE TABLE  IF NOT EXISTS  tbl_fav(id INTEGER NOT NULL)";
        sqldb.execSQL(qury);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
    }

    public void getDishList(List <ListItemDishes> list)
    {
        String query = "SELECT * FROM tbl_dishes";
        Cursor data = sqldb.rawQuery(query, null);
        Cursor favorite = sqldb.rawQuery("SELECT * FROM tbl_fav", null);
        List<Integer> favArray = new ArrayList<>(favorite.getCount());
        int sizeFav = favorite.getCount();

        while (favorite.moveToNext())
        {
            favArray.add(favorite.getInt(0));
        }

        while (data.moveToNext())
        {
            ListItemDishes dish = new ListItemDishes(data.getInt(0),
                    data.getInt(1),
                    data.getString(2),
                    data.getString(3),
                    data.getInt(5),
                    data.getInt(6),
                    data.getInt(4),
                    data.getInt(7),
                    data.getInt(9),
                    data.getString(8)
                    );
            list.add(dish);

            for ( int i = 0; i < sizeFav; i++)
            {
                if (dish.getId() == favArray.get(i))
                {
                    dish.setFav(true);
                    print(dish.getId() + " is favrite");
                }
            }
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

    public void clearDB()
    {
        String query = "DELETE FROM tbl_dishes";
        sqldb.execSQL(query);
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

    public boolean isFavorite(int id)
    {
        String query = "SELECT id FROM tbl_fav WHERE id="+id;
        Cursor checkDatax = sqldb.rawQuery(query, null);
        print("JKS count = "+checkDatax.getCount());
        if (sqldb.rawQuery(query, null).getCount() == 0) {
            checkDatax.close();
            return false;
        }
        else {
            checkDatax.close();
            return true;
        }
    }
    public void clearFavorite(int id)
    {
        String query = "Delete from tbl_fav where id="+id;
        sqldb.execSQL(query);
    }

    public void setFavorite(int id)
    {
        String query = "INSERT into tbl_fav VALUES ("+id+")";
        sqldb.execSQL(query);
    }

}
