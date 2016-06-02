package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    int itemCount = 0;

    public String insertQuerry = "INSERT INTO billData (refId,stockId) values (null, null);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_make_bill);

        itemCount = 0;
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

                if(itemCount ==0)
                {
                    Toast.makeText(getBaseContext(),"Please select products",Toast.LENGTH_LONG).show();
                    return;
                }

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


                String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+MainActivity.billRefId;
                Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
                Log.d("JKS", "Get " + c2.getCount());


                if(c2.getCount() != 0) {
                    while (c2.moveToNext()) {
                        int itemsSold_q = 0;
                        String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId="+c2.getInt(0);
                        Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                        if(c4.getCount() == 1) {
                            c4.moveToNext();
                            Log.d("JKS", "Items Sold = " + c4.getInt(0));
                            itemsSold_q = c4.getInt(0);
                        }
                        String updateQuerry = "UPDATE stockData SET itemsSold = "+(itemsSold_q - 1)+" WHERE stockId="+c2.getInt(0);
                        Log.d("JKS","update Querry = "+updateQuerry);
                        MainActivity.mdb.execSQL(updateQuerry);
                    }
                }

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

                if(txt_serial.getText().toString().matches(""))
                {
                    Toast.makeText(getBaseContext(), "Scan barcode of product or enter serial number",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                int serialNum = Integer.parseInt(txt_serial.getText().toString());


                Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId,noOfItems,itemsSold FROM stockData WHERE serialNumber='" + serialNum + "'", null);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txt_serial.getWindowToken(), 0);


                TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
                TableRow tr1 = new TableRow(ctx);
                if (c3.getCount() != 0) {


                    Log.d("JKS", " scan Result");
                    while (c3.moveToNext()) {
                        int totalItems = c3.getInt(4);
                        int itemsSold = c3.getInt(5);
                        if(itemsSold>=totalItems)
                        {
                            Log.d("JKS","Soldall items "+itemsSold+">="+totalItems);
                            txt_serial.setText("");
                            Toast.makeText(getBaseContext(), "This item is out of stock",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        itemCount++;
                        Log.d("JKS", "slNum = " + c3.getString(0) + " item = " + c3.getInt(1) + " price = " + c3.getInt(2)+" stockId ="+c3.getInt(3));
                        insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values ("+MainActivity.billRefId+","+c3.getInt(3)+");";
                        MainActivity.mdb.execSQL(insertQuerry);
                        Log.d("JKS", "Ins Query = " + insertQuerry);
                        insertQuerry ="";

                        int itemsSold_q = 0;
                        String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId="+c3.getInt(3);
                        Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                        if(c4.getCount() == 1) {
                            c4.moveToNext();
                            Log.d("JKS", "Items Sold = " + c4.getInt(0));
                            itemsSold_q = c4.getInt(0);
                        }
                        String updateQuerry = "UPDATE stockData SET itemsSold = "+(itemsSold_q + 1)+" WHERE stockId="+c3.getInt(3);
                        Log.d("JKS","update Querry = "+updateQuerry);
                        MainActivity.mdb.execSQL(updateQuerry);

                        TextView textview = new TextView(ctx);
                        TextView textview2 = new TextView(ctx);
                        TextView textview3 = new TextView(ctx);
                        textview.setTextColor(Color.parseColor("#FFFFFF"));
                        textview2.setTextColor(Color.parseColor("#FFFFFF"));
                        textview3.setTextColor(Color.parseColor("#FFFFFF"));
                        textview.setText(c3.getString(0));


                        switch (c3.getInt(1)) {
                            case 0:textview2.setText("SHIRT  ");break;
                            case 1:textview2.setText("JEANS  ");break;
                            case 2:textview2.setText("OTHERS ");break;
                            case 3:textview2.setText("PANTS  ");break;
                            case 4:textview2.setText("T-SHIRT");break;
                            case 5:textview2.setText("BELT   ");break;
                            case 6:textview2.setText("INNER  ");break;
                            case 7:textview2.setText("SHORTS ");break;
                            case 8:textview2.setText("WALLET ");break;
                            case 9:textview2.setText("OTHERS ");break;
                            default:textview2.setText("OTHERS ");
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


            Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId,noOfItems,itemsSold FROM stockData WHERE barcodeId='" + scanContent + "'", null);

            TableLayout tl = (TableLayout) findViewById(R.id.tbl_bill);
            TableRow tr1 = new TableRow(ctx);
            if (c3.getCount() != 0) {

                while (c3.moveToNext()) {
                    int totalItems = c3.getInt(4);
                    int itemsSold = c3.getInt(5);
                    if(itemsSold>=totalItems)
                    {
                        Toast.makeText(getBaseContext(), "This item is out of stock",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    itemCount++;

                    insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values ("+MainActivity.billRefId+","+c3.getInt(3)+");";
                    MainActivity.mdb.execSQL(insertQuerry);

                    insertQuerry ="";

                    int itemsSold_q = 0;
                    String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId="+c3.getInt(3);
                    Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                    if(c4.getCount() == 1) {
                        c4.moveToNext();

                        itemsSold_q = c4.getInt(0);
                    }
                    String updateQuerry = "UPDATE stockData SET itemsSold = "+(itemsSold_q + 1)+" WHERE stockId="+c3.getInt(3);

                    MainActivity.mdb.execSQL(updateQuerry);

                    TextView textview = new TextView(ctx);
                    TextView textview2 = new TextView(ctx);
                    TextView textview3 = new TextView(ctx);
                    textview.setTextColor(Color.parseColor("#FFFFFF"));
                    textview2.setTextColor(Color.parseColor("#FFFFFF"));
                    textview3.setTextColor(Color.parseColor("#FFFFFF"));
                    textview.setText(c3.getString(0));


                    switch (c3.getInt(1)) {
                        case 0:textview2.setText("SHIRT  ");break;
                        case 1:textview2.setText("JEANS  ");break;
                        case 2:textview2.setText("OTHERS ");break;
                        case 3:textview2.setText("PANTS  ");break;
                        case 4:textview2.setText("T-SHIRT");break;
                        case 5:textview2.setText("BELT   ");break;
                        case 6:textview2.setText("INNER  ");break;
                        case 7:textview2.setText("SHORTS ");break;
                        case 8:textview2.setText("WALLET ");break;
                        case 9:textview2.setText("OTHERS ");break;
                        default:textview2.setText("OTHERS ");
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
            }


        }else{
          Log.d("JKS","ERRROR in reading barcode");
        }
    }
}
