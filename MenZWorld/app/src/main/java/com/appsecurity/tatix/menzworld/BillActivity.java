package com.appsecurity.tatix.menzworld;

import android.content.Intent;
import android.database.Cursor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        int total = 0;
        int discount = 0;

        Intent intent = getIntent();
        int billNo = intent.getIntExtra("billno", 0);
        discount = intent.getIntExtra("discount",0);
        Log.d("JKS","Bill no = "+billNo);
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



        int refId = 0;
        String getRefIdQuerry = "SELECT refId FROM billTable WHERE billId="+billNo;
        Log.d("JKS"," select querry = "+getRefIdQuerry);

        Cursor c = MainActivity.mdb.rawQuery(getRefIdQuerry, null);
        Log.d("JKS","Get "+c.getCount());

        if(c.getCount() == 1) {
            c.moveToNext();
            Log.d("JKS ", "refId is " + c.getInt(0));
            refId = c.getInt(0);
        }
        String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+refId;

        Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
        Log.d("JKS", "Get " + c2.getCount());


        if(c2.getCount() != 0) {
            while(c2.moveToNext()){

                Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,price,stockId FROM stockData WHERE stockId='" + c2.getInt(0) + "'", null);
                Log.d("JKS","stock Id = "+c2.getInt(0));
                if(c3.getCount() != 0) {
                    while (c3.moveToNext()) {

                        Log.d("JKS", "slNum = " + c3.getString(0) + " item = " + c3.getInt(1) + " price = " + c3.getInt(2)+" stockId ="+c3.getInt(3));
                        TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
                        TableRow tr1 = new TableRow(BillActivity.this);
                        TextView textview = new TextView(BillActivity.this);
                        TextView textview2 = new TextView(BillActivity.this);
                        TextView textview3 = new TextView(BillActivity.this);
                        textview.setText(c3.getString(0));


                        switch (c3.getInt(1)) {
                            case 0:

                                textview2.setText("SHIRT");
                                break;
                            case 1:

                                textview2.setText("JEANS");
                                break;
                            case 2:

                                textview2.setText("OTHERS");
                                break;
                            case 3:

                                textview2.setText("PANTS");
                                break;
                            case 4:

                                textview2.setText("T-SHIRT");
                                break;
                            case 5:

                                textview2.setText("BELT");
                                break;
                            case 6:

                                textview2.setText("INNER");
                                break;
                            case 7:

                                textview2.setText("SHORTS");
                                break;
                            case 8:

                                textview2.setText("WALLET");
                                break;
                            case 9:

                                textview2.setText("OTHERS");
                                break;
                            default:
                                textview2.setText("OTHERS");
                        }

                        textview3.setText(c3.getString(2));
                        total += c3.getInt(2);

                       /* if(textview.getParent()!=null)
                            ((ViewGroup)textview.getParent()).removeView(textview);*/
                        tr1.addView(textview);
                        /*if(textview2.getParent()!=null)
                            ((ViewGroup)textview2.getParent()).removeView(textview2);*/
                        tr1.addView(textview2);
                   /*     if(textview3.getParent()!=null)
                            ((ViewGroup)textview3.getParent()).removeView(textview3);*/
                        tr1.addView(textview3);
/*                        if(tr1.getParent()!=null)
                            ((ViewGroup)tr1.getParent()).removeView(tr1);*/
                        tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                }


            }
        }

        txt_total.setText("RS: " + total);
        txt_grant_total.setText("RS:  " +(total - discount));




    }
}
