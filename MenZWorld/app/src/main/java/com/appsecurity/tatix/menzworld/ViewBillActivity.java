package com.appsecurity.tatix.menzworld;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ViewBillActivity extends AppCompatActivity {

    static ListView billList;


    String [] date;
    String [] billId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_bill);



        int nCount = 0;

        Cursor c = MainActivity.mdb.rawQuery("SELECT billDate,billId FROM billTable", null);

        nCount = c.getCount();
        if(nCount != 0)
        {
            date = new String[nCount+1];
            billId = new String[nCount+1];
            int index = 0;
            date[index] ="DATE";
            billId[index] ="BillID";
            index++;

            while (c.moveToNext()) {
                date[index] = c.getString(0);
                billId[index] = c.getString(1);

                Log.d("JKS", "" + c.getString(0) + " ," + c.getString(1) );

                index++;
            }


            CustomListBill adapter = new
                    CustomListBill(ViewBillActivity.this, date, billId, nCount);


            billList = (ListView)findViewById(R.id.list_bill);
            billList.setAdapter(adapter);
            billList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if(position ==0) return;

                    Log.d("JKS","position= "+billId[position]);
                    int billTblId = Integer.parseInt(billId[position]);
                    String dateTime = "";
                    int discount = 0;

                    String gebillDateQuerry = "SELECT billDate FROM billTable WHERE billId="+billTblId;
                    Log.d("JKS"," select querry = "+gebillDateQuerry);

                    Cursor c = MainActivity.mdb.rawQuery(gebillDateQuerry, null);
                    Log.d("JKS","Get "+c.getCount());

                    if(c.getCount() == 1) {
                        c.moveToNext();
                        Log.d("JKS ", "date is " + c.getInt(0));
                        dateTime = c.getString(0);
                    }

                    String gediscountQuerry = "SELECT discount FROM billTable WHERE billId="+billTblId;
                    Log.d("JKS"," select querry = "+gediscountQuerry);

                    Cursor c2 = MainActivity.mdb.rawQuery(gediscountQuerry, null);
                    Log.d("JKS","Get "+c2.getCount());

                    if(c2.getCount() == 1) {
                        c2.moveToNext();
                        Log.d("JKS ", "discount is " + c2.getInt(0));
                        discount = c2.getInt(0);
                    }

                    Intent intent = new Intent(ViewBillActivity.this, BillActivity.class);
                    intent.putExtra("billno", billTblId);
                    intent.putExtra("date",dateTime);
                    intent.putExtra("discount",discount);
                    startActivity(intent);

                }
            });

        }
    }
}
