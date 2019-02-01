package com.jaapps.cookery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
                "type INTEGER (64) NOT NULL,"+
                "dishname varchar(56) NOT NULL,"+
                "img_path varchar(56) NOT NULL,"+
                "calory INTEGER NOT NULL,"+
                "cooktimeinsec INTEGER NOT NULL,"+
                "serves INTEGER NOT NULL,"+
                "rating INTEGER NOT NULL,"+
                "author varchar(64) NOT NULL,"+
                "numRating INTEGER NOT NULL," +
                "cuktime varchar (16))";

        sqldb.execSQL(qury);

        //Create table where favorites dishes are kept
        qury = "CREATE TABLE  IF NOT EXISTS  tbl_fav(id INTEGER NOT NULL)";
        sqldb.execSQL(qury);

        //Create table to save the shopping list
        qury = "CREATE TABLE  IF NOT EXISTS  tbl_shopList(id INTEGER NOT NULL, type INTEGER, data varchar(128))";
        sqldb.execSQL(qury);
    }

    @Override
    public void onCreate(SQLiteDatabase sqldb) {
    }

    public void getDishOfType (int inType , List <ListItemDishes> list) {
        String query = "SELECT * FROM tbl_dishes Where (type & "+inType+" != 0)";
        Cursor data = sqldb.rawQuery(query, null);
        while (data.moveToNext()) {
            ListItemDishes dish = new ListItemDishes(data.getInt(0),
                    data.getInt(1),
                    data.getString(2),
                    data.getString(3),
                    data.getInt(5),
                    data.getInt(6),
                    data.getInt(4),
                    data.getInt(7),
                    data.getInt(9),
                    data.getString(8),
                    data.getString(10)
            );
            list.add(dish);

        }
    }

    public int getSearchData(List<ListItemDishes> list, String text) {
        String query = "SELECT * FROM tbl_dishes Where dishname like '%" + text + "%'";
        Cursor data = sqldb.rawQuery(query, null);
        while (data.moveToNext()) {
            ListItemDishes dish = new ListItemDishes(data.getInt(0),
                    data.getInt(1),
                    data.getString(2),
                    data.getString(3),
                    data.getInt(5),
                    data.getInt(6),
                    data.getInt(4),
                    data.getInt(7),
                    data.getInt(9),
                    data.getString(8),
                    data.getString(10)
            );
            list.add(dish);

        }
        return list.size();
    }

    public void getFavList(List<ListItemDishes> list)
    {
        String query ="";

        Cursor favorite = sqldb.rawQuery("SELECT * FROM tbl_fav", null);
        while (favorite.moveToNext())
        {
            if(query.length() == 0)
            {
                query = "SELECT * FROM tbl_dishes Where id = "+favorite.getInt(0);
            }
            else
            {
                query += " or id= "+favorite.getInt(0);
            }
        }
        if (query.length() != 0) {
            Cursor data = sqldb.rawQuery(query, null);
            while (data.moveToNext()) {
                ListItemDishes dish = new ListItemDishes(data.getInt(0),
                        data.getInt(1),
                        data.getString(2),
                        data.getString(3),
                        data.getInt(5),
                        data.getInt(6),
                        data.getInt(4),
                        data.getInt(7),
                        data.getInt(9),
                        data.getString(8),
                        data.getString(10)
                );
                list.add(dish);

            }
        }
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
                    data.getString(8),
                    data.getString(10)
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

        data.close();
        favorite.close();
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

    long startTime;
    long endTime;

    private Lock _mutex = new ReentrantLock(true);

    public void syncDB(List<ListItemDishes> list)
    {
        int tid;
        int startCount;
        int dishCount = list.size();
        SyncThread thread [] = new SyncThread[100];

        startCount = 0;
        tid = 0;
        startTime = Calendar.getInstance().getTimeInMillis();

        SyncThread threadSync = new SyncThread(tid, startCount, dishCount, list );
        new Thread(threadSync).start();

        /*while (dishCount > 200)
        {
            thread[tid] = new SyncThread(tid, startCount, 200, list );
            new Thread(thread[tid] ).start();
            tid++;
            startCount += 200;
            dishCount -= 200;
        }
        if (dishCount != 0)
        {
            thread[tid] = new SyncThread(tid, startCount, dishCount, list );
            new Thread(thread[tid] ).start();
            tid++;
        }*/


        /*
        for ( ListItemDishes dish:
              list) {
            String name = dish.getName().replace("'","''");
            String author = dish.getAuthor().replace("'","''");
            String query="INSERT INTO tbl_dishes (id, type, dishname, img_path, calory, cooktimeinsec, serves, author, rating, numRating, cuktime) VALUES ("+
                    dish.getId() +", "+
                    dish.getType() + ", '"+
                    name +"','"+
                    dish.getImg_path() +"',"+
                    dish.getCalory() +","+
                    dish.getCooktimeinsec() +","+
                    dish.getServeCount() +",'"+
                    author + "',"+
                    dish.getRating() + ","+
                    dish.getNumRating()+",'"+
                    dish.getCuktime() +"')";
            sqldb.execSQL(query);
        }
        */
        endTime = Calendar.getInstance().getTimeInMillis();
        print("JKS sync Time = "+(endTime - startTime));
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

    public void addItemToShopList(String igrd, String dish, int id)
    {
        String query = "SELECT id FROM tbl_shopList WHERE id="+id;
        Cursor checkDatax = sqldb.rawQuery(query, null);
        if (checkDatax.getCount() == 0)
        {
            query = "INSERT into tbl_shopList (id, type, data)VALUES ("+id+", 0, '"+dish+"')";
            sqldb.execSQL(query);
        }

        query = "INSERT into tbl_shopList (id, type, data)VALUES ("+id+", 1, '"+igrd+"')";
        sqldb.execSQL(query);
        checkDatax.close();
    }

    public void removeItemFromShopList(String ingredient, int id)
    {
        String query = "DELETE from tbl_shopList where id="+id + " AND data like '"+ingredient+"'";
        sqldb.execSQL(query);

        //Check if last entry is removed
        query = "SELECT id FROM tbl_shopList WHERE id="+id;
        Cursor checkDatax = sqldb.rawQuery(query, null);
        if (checkDatax.getCount() == 1)
        {
            query = "DELETE from tbl_shopList where id="+id;
            sqldb.execSQL(query);
        }
        checkDatax.close();
    }

    public void getShopListData(List<ShoppingListItem> list) {
        String query = "SELECT * from tbl_shopList";
        Cursor checkDatax = sqldb.rawQuery(query, null);

        while (checkDatax.moveToNext()) {
            ShoppingListItem listItemData = new ShoppingListItem();
            listItemData.id = checkDatax.getInt(0);
            listItemData.type = checkDatax.getInt(1);
            listItemData.data = checkDatax.getString(2);
            list.add(listItemData);
        }
        checkDatax.close();
    }

    class SyncThread implements  Runnable
    {
        int startCount;
        int endCount;
        List <ListItemDishes> list;
        int id;

        public SyncThread(int id, int startCount, int endCount, List<ListItemDishes> list) {
            this.startCount = startCount;
            this.endCount = endCount;
            this.list = list;
            this.id = id;

            print(" ["+id+"] Spporsed to start From ["+this.startCount + " - "+this.endCount +"]");
        }

        public void run()
        {

            print(" ["+id+"] From ["+startCount + " - "+endCount +"]");
            for (int i = this.startCount ; i < this.endCount  ; i++)
            {
                ListItemDishes dish = list.get(i);
                String name = dish.getName().replace("'","''");
                String author = dish.getAuthor().replace("'","''");

                String query="INSERT INTO tbl_dishes (id, type, dishname, img_path, calory, cooktimeinsec, serves, author, rating, numRating, cuktime) VALUES ("+
                        dish.getId() +", "+
                        dish.getType() + ", '"+
                        name +"','"+
                        dish.getImg_path() +"',"+
                        dish.getCalory() +","+
                        dish.getCooktimeinsec() +","+
                        dish.getServeCount() +",'"+
                        author + "',"+
                        dish.getRating() + ","+
                        dish.getNumRating()+",'"+
                        dish.getCuktime() +"')";
                sqldb.execSQL(query);
            }
            endTime = Calendar.getInstance().getTimeInMillis();
            print(" ["+id+"] sync time = "+(endTime - startTime));

        }
    }
}
