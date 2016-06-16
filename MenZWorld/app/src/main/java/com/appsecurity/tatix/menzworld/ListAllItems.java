package com.appsecurity.tatix.menzworld;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListAllItems extends AppCompatActivity {

    static ListView stockList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list_all_items);

        String [] serialNo;
        String [] itemType;
        String [] price;
        String [] sellingPrice;
        String [] noOfItems;
        String [] soldItems;
        String [] available;
        int nCount = 0;

        Cursor c = MainActivity.mdb.rawQuery("SELECT serialNumber,item,price,noOfItems,selling_price,itemsSold FROM stockData WHERE noOfItems>itemsSold", null);

        nCount = c.getCount();
        if(nCount != 0)
        {
            serialNo = new String[nCount+1];
            itemType = new String[nCount+1];
            price = new String[nCount+1];
            noOfItems = new String[nCount+1];
            soldItems = new  String[nCount+1];
            sellingPrice = new  String[nCount+1];
            available = new String[nCount + 1];

            int index = 0;
            serialNo[index] = "Sl no";
            itemType[index] = "Item type";
            price[index] = "Price";
            noOfItems[index] = "Total";
            sellingPrice[index] = "Retail";
            soldItems[index]= "Sold";
            available[index] = "Available";
            index++;

            while (c.moveToNext()) {
                serialNo[index] = c.getString(0);
                switch (c.getInt(1)) {
                    case 0:itemType[index] = ("SHIRT   ");break;
                    case 1:itemType[index] = ("JEANS   ");break;
                    case 2:itemType[index] = ("OTHERS  ");break;
                    case 3:itemType[index] = ("PANTS   ");break;
                    case 4:itemType[index] = ("T-SHIRT ");break;
                    case 5:itemType[index] = ("BELT    ");break;
                    case 6:itemType[index] = ("INNER   ");break;
                    case 7:itemType[index] = ("SHORTS  ");break;
                    case 8:itemType[index] = ("WALLET  ");break;
                    case 9:itemType[index] = ("OTHERS  ");break;
                    default:itemType[index] = ("OTHERS ");
                }

                price[index] = c.getString(2);
                noOfItems[index] = c.getString(3);
                sellingPrice[index] = c.getString(4);
                soldItems[index]= c.getString(5);
                available[index] = (Integer.parseInt(noOfItems[index]) - Integer.parseInt(soldItems[index]))+"";

                index++;
            }


            customListAll adapter = new
                    customListAll(ListAllItems.this, serialNo, itemType, price,noOfItems,sellingPrice,available,nCount+1);


            stockList = (ListView)findViewById(R.id.list_all);
            stockList.setAdapter(adapter);
            stockList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {


                }
            });

        }

    }

}
