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
                index++;
            }


            CustomListBill adapter = new
                    CustomListBill(ViewBillActivity.this, date, billId, nCount+1);


            billList = (ListView)findViewById(R.id.list_bill);
            billList.setAdapter(adapter);
            billList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if(position ==0) return;
                    int billTblId = Integer.parseInt(billId[position]);
                    String dateTime = "";
                    int discount = 0;

                    String gebillDateQuerry = "SELECT billDate FROM billTable WHERE billId="+billTblId;


                    Cursor c = MainActivity.mdb.rawQuery(gebillDateQuerry, null);


                    if(c.getCount() == 1) {
                        c.moveToNext();

                        dateTime = c.getString(0);
                    }

                    String gediscountQuerry = "SELECT discount FROM billTable WHERE billId="+billTblId;


                    Cursor c2 = MainActivity.mdb.rawQuery(gediscountQuerry, null);


                    if(c2.getCount() == 1) {
                        c2.moveToNext();

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
