package com.appsecurity.tatix.menzworld;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BillActivity extends AppCompatActivity {

    int billNo = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bill);
        int total = 0;
        int discount = 0;

        Intent intent = getIntent();
        billNo = intent.getIntExtra("billno", 0);
        discount = intent.getIntExtra("discount",0);

        TextView txtBillNo = (TextView)findViewById(R.id.txt_billNo);
        txtBillNo.setText("" + billNo);
        TextView txtDate = (TextView)findViewById(R.id.txt_dateB);
        txtDate.setText("" + intent.getStringExtra("date"));

        TextView txt_total = (TextView)findViewById(R.id.txt_bill_total);
        TextView txt_discount = (TextView)findViewById(R.id.txt_bill_discount);
        TextView txt_grant_total = (TextView)findViewById(R.id.txt_bill_grant);
        txt_total.setText("0");
        txt_grant_total.setText("0");
        txt_discount.setText(""+intent.getIntExtra("discount",0));

        Button btn_ok =(Button)findViewById(R.id.btn_ok_bill);

        btn_ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        finish();
                    }
                });

        Button btn_edit = (Button)findViewById(R.id.btn_edit_bill);
        btn_edit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(BillActivity.this, MakeBillActivity.class);
                        intent.putExtra("billno", billNo);
                        startActivity(intent);
                        finish();
                    }
                });



        int refId = 0;
        String getRefIdQuerry = "SELECT refId FROM billTable WHERE billId="+billNo;


        Cursor c = MainActivity.mdb.rawQuery(getRefIdQuerry, null);


        if(c.getCount() == 1) {
            c.moveToNext();
            refId = c.getInt(0);
        }
        String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+refId;

        Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);


        if(c2.getCount() != 0) {
            while(c2.moveToNext()){

                Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId,brand,size FROM stockData WHERE stockId='" + c2.getInt(0) + "'", null);

                if(c3.getCount() != 0) {
                    while (c3.moveToNext()) {
                        TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
                        TableRow tr1 = new TableRow(BillActivity.this);
                        TextView textview = new TextView(BillActivity.this);
                        TextView textview2 = new TextView(BillActivity.this);
                        TextView textview3 = new TextView(BillActivity.this);
                        TextView textview4 = new TextView(BillActivity.this);
                        TextView textview5 = new TextView(BillActivity.this);
                        textview.setTextColor(Color.parseColor("#FFFFFF"));
                        textview2.setTextColor(Color.parseColor("#FFFFFF"));
                        textview3.setTextColor(Color.parseColor("#FFFFFF"));
                        textview4.setTextColor(Color.parseColor("#FFFFFF"));
                        textview5.setTextColor(Color.parseColor("#FFFFFF"));
                        textview.setText(c3.getString(0));


                        switch (c3.getInt(1)) {
                            case 0:textview2.setText("SHIRT   ");break;
                            case 1:textview2.setText("JEANS   ");break;
                            case 2:textview2.setText("OTHERS  ");break;
                            case 3:textview2.setText("PANTS   ");break;
                            case 4:textview2.setText("T-SHIRT ");break;
                            case 5:textview2.setText("BELT    ");break;
                            case 6:textview2.setText("INNER   ");break;
                            case 7:textview2.setText("SHORTS  ");break;
                            case 8:textview2.setText("WALLET  ");break;
                            case 9:textview2.setText("OTHERS  ");break;
                            default:textview2.setText("OTHERS  ");
                        }
                        textview5.setText(c3.getString(5)+"  ");
                        switch (c3.getInt(5))
                        {
                            case 0:   textview5.setText("SMALL    ");break;
                            case 1:   textview5.setText("MEDIUM   ");break;
                            case 2:   textview5.setText("LARGE    ");break;
                            case 3:   textview5.setText("X-LARGE  ");break;
                            case 4:   textview5.setText("XX-LARGE ");break;
                            case -1: textview5.setText ("N A      ") ; break;
                            default:  textview5.setText(c3.getString(5)+ "     ");break;
                        }
                        textview3.setText(c3.getString(2));
                        textview4.setText(c3.getString(4));

                        total += c3.getInt(2);

                        //tr1.addView(textview);
                        tr1.addView(textview2);
                        tr1.addView(textview4);
                        tr1.addView(textview5);
                        tr1.addView(textview3);
                        tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                }


            }
        }

        txt_total.setText("RS: " + total);
        txt_grant_total.setText("RS:  " +(total - discount));




    }
}
