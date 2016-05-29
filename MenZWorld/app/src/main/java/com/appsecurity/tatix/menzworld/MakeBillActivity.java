package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MakeBillActivity extends AppCompatActivity {

    int total = 0;
    Context ctx ;
    public String insertQuerry = "INSERT INTO billData (refId,stockId) values (null,null);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_bill);

        total = 0;
        ctx = MakeBillActivity.this;
        insertQuerry="";

        Button btn_cancel = (Button)findViewById(R.id.btn_cancel_bill);
        Button btn_scan = (Button)findViewById(R.id.btn_scan_item);
        Button btn_getItem = (Button)findViewById(R.id.btn_getItem);

        Button genBill = (Button)findViewById(R.id.btn_gen_bill);
        genBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editDiscount = (EditText)findViewById(R.id.edit_discount);

                int refId = MainActivity.billRefId;
                int discount = Integer.parseInt(editDiscount.getText().toString());
                String dateTime = AddItemActivity.getDateTime();
                String billQuerry = "INSERT INTO billTable (refId,discount,billDate) values("+MainActivity.billRefId +","+discount+",'"+dateTime+"');";
                Log.d("JKS", "Bill Query = " + billQuerry);
                MainActivity.mdb.execSQL(billQuerry);

                insertQuerry="";
                billQuerry="";

                Intent intent = new Intent(MakeBillActivity.this, BillActivity.class);

                String getBillIdQuerry = "SELECT billId FROM billTable WHERE refId="+refId;
                Log.d("JKS"," select querry = "+getBillIdQuerry);

                Cursor c = MainActivity.mdb.rawQuery(getBillIdQuerry, null);
                Log.d("JKS","Get "+c.getCount());

                int billId = 0;
                if(c.getCount() == 1) {
                    c.moveToNext();
                    Log.d("JKS ", "bill id is is " + c.getInt(0));
                    billId = c.getInt(0);
                }

                String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+refId;
                Log.d("JKS"," select querry = "+getStockIdQuerry);

                Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
                Log.d("JKS","Get "+c2.getCount());


                if(c2.getCount() == 1) {
                    while(c2.moveToNext())
                    {
                        Log.d("JKS","stockId = "+c2.getInt(0));
                        int itemsSold = 0;
                        String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId="+c2.getInt(0);
                        Cursor c3 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                        if(c3.getCount() == 1) {
                            c3.moveToNext();
                            Log.d("JKS", "Items Sold = " + c3.getInt(0));
                            itemsSold = c3.getInt(0);
                        }

                        String updateQuerry = "UPDATE stockData SET itemsSold = "+(itemsSold + 1)+" WHERE billData WHERE stockId="+c2.getInt(0);
                        Log.d("JKS","update Querry = "+updateQuerry);
                        MainActivity.mdb.execSQL(updateQuerry);
                    }
                }

                intent.putExtra("billno", billId);
                intent.putExtra("date",dateTime);
                intent.putExtra("discount",discount);
                startActivity(intent);
                MainActivity.billRefId++;
                finish();

            }
        });

        Button btn_calc = (Button)findViewById(R.id.btn_calculate);
        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txt_grant = (TextView)findViewById(R.id.txt_grant_total);
                EditText editDiscount = (EditText)findViewById(R.id.edit_discount);
                int discount = Integer.parseInt(editDiscount.getText().toString());
                txt_grant.setText("RS: " + (total - discount));
            }
        });


        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(MakeBillActivity.this);

                scanIntegrator.initiateScan();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertQuerry = " DELETE FROM billData  WHERE billrefId ="+MainActivity.billRefId;
                MainActivity.mdb.execSQL(insertQuerry);
                insertQuerry ="";

                finish();
            }
        });

        btn_getItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText txt_serial = (EditText) findViewById(R.id.edit_serial);

                int serialNum = Integer.parseInt(txt_serial.getText().toString());


                Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId FROM stockData WHERE serialNumber='" + serialNum + "'", null);



                TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
                TableRow tr1 = new TableRow(ctx);
                if (c3.getCount() != 0) {


                    Log.d("JKS", " scan Result");
                    while (c3.moveToNext()) {

                        Log.d("JKS", "slNum = " + c3.getString(0) + " item = " + c3.getInt(1) + " price = " + c3.getInt(2)+" stockId ="+c3.getInt(3));
                        insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values ("+MainActivity.billRefId+","+c3.getInt(3)+");";
                        MainActivity.mdb.execSQL(insertQuerry);
                        Log.d("JKS", "Ins Query = " + insertQuerry);
                        insertQuerry ="";

                        TextView textview = new TextView(ctx);
                        TextView textview2 = new TextView(ctx);
                        TextView textview3 = new TextView(ctx);
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
                        TextView txt_total = (TextView) findViewById(R.id.txt_total);
                        txt_total.setText("RS: " + total);


                        tr1.addView(textview);

                        tr1.addView(textview2);
                        tr1.addView(textview3);
                        tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    }
                } else {
                    Log.d("JKS", "No results for the barcode scaned");
                    Log.d("JKS", "Available are");
                    Cursor c4 = MainActivity.mdb.rawQuery("SELECT * FROM stockData", null);

                    if (c4.getCount() != 0) {
                        while (c4.moveToNext()) {
                            Log.d("JKS", "barcodeId = " + c4.getString(0) + " item = " + c4.getInt(1) + " price = " + c4.getInt(2) + " selling price" + c4.getInt(3) + " solding price = " + c4.getInt(4));
                        }
                    }

                }
                txt_serial.setText("");
            }


        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();


            Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,price FROM stockData WHERE barcodeId='" + scanContent + "'", null);

            TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
            TableRow tr1 = new TableRow(ctx);
            if (c3.getCount() != 0) {
                Log.d("JKS", " scan Result");
                while (c3.moveToNext()) {

                    Log.d("JKS", "slNum = " + c3.getString(0) + " item = " + c3.getInt(1) + " price = " + c3.getInt(2));


                    TextView textview = new TextView(ctx);
                    TextView textview2 = new TextView(ctx);
                    TextView textview3 = new TextView(ctx);
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
                    TextView txt_total = (TextView) findViewById(R.id.txt_total);
                    txt_total.setText("RS: " + total);


                    tr1.addView(textview);

                    tr1.addView(textview2);
                    tr1.addView(textview3);
                    tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }
            } else {
                Log.d("JKS", "No results for the barcode scaned");
                Log.d("JKS", "Available are");
                Cursor c4 = MainActivity.mdb.rawQuery("SELECT * FROM stockData", null);

                if (c4.getCount() != 0) {
                    while (c4.moveToNext()) {
                        Log.d("JKS", "barcodeId = " + c4.getString(0) + " item = " + c4.getInt(1) + " price = " + c4.getInt(2) + " selling price" + c4.getInt(3) + " solding price = " + c4.getInt(4));
                    }
                }

            }


        }else{
          Log.d("JKS","ERRROR in reading barcode");
        }
    }
}
