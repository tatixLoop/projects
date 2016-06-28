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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
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
    private String[] itemCode;
    private String[] item;
    private String[] price;
    private String[] brand;
    private String[] size;
    private int[] removedSlNo;
    private int[] newItem;
    private int removedIndex;
    private int newItemIndex;
    private int numItemsInbill;
    private  int curBillRefId = -1;

    private  CustomListMakeBillList adapter;
    static ListView billItems;

    boolean editBillFlag;

    public String insertQuerry = "INSERT INTO billData (refId,stockId) values (null, null);";

    @Override
    public void onBackPressed() {
        cancel_DatasRecorded();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_make_bill);

        curBillRefId = MainActivity.billRefId;

        itemCount = 0;
        total = 0;
        ctx = MakeBillActivity.this;
        insertQuerry="";

        numItemsInbill = 0;
        itemCode = new String[100];
        item = new String [100];
        price = new String[100];
        brand = new String[100];
        size = new String[100];
        removedSlNo = new int[100];
        newItem = new int [100];
        removedIndex = 0;

        itemCode[numItemsInbill] = "CODE";
        item[numItemsInbill] = "ITEM";
        brand[numItemsInbill]= "BRAND";
        size[numItemsInbill] = "SIZE";
        price[numItemsInbill] = "PRICE";
        numItemsInbill++;

        // get previous bill items if it is edit
        Intent intent = getIntent();
        final int billNo = intent.getIntExtra("billno", -1);
        if(billNo == -1)
        {
            Log.d("JKS","CREATE NEW BILL");
            editBillFlag = false;
        }
        else {
            Log.d("JKS", "EDIT BILL");
            newItemIndex= 0;
            editBillFlag = true;

            int refId = 0;
            String getRefIdQuerry = "SELECT refId FROM billTable WHERE billId="+billNo;
            Cursor c = MainActivity.mdb.rawQuery(getRefIdQuerry, null);

            if(c.getCount() == 1) {
                c.moveToNext();
                refId = c.getInt(0);
            }

            curBillRefId = refId;

            String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+refId;
            Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);


            if(c2.getCount() != 0) {
                while(c2.moveToNext()){

                    Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId,noOfItems,itemsSold,brand,size FROM stockData WHERE stockId='" + c2.getInt(0) + "'", null);

                    if(c3.getCount() != 0) {
                        while (c3.moveToNext()) {



                            itemCount++;
                            itemCode[numItemsInbill] = c3.getString(0);
                            switch (c3.getInt(1)) {

                                case 0: item[numItemsInbill] = "SHIRT";break;
                                case 1: item[numItemsInbill] = "JEANS"; break;
                                case 2:item[numItemsInbill] = "OTHERS"; break;
                                case 3:item[numItemsInbill] = "PANTS"; break;
                                case 4:item[numItemsInbill] = "T-SHIRT"; break;
                                case 5: item[numItemsInbill] = "BELT"; break;
                                case 6: item[numItemsInbill] = "INNER"; break;
                                case 7: item[numItemsInbill] = "SHORTS"; break;
                                case 8: item[numItemsInbill] = "WALLET"; break;
                                case 9: item[numItemsInbill] = "OTHERS"; break;
                                default:item[numItemsInbill] = "OTHERS";
                            }

                            price[numItemsInbill] = c3.getString(2);
                            brand[numItemsInbill]= c3.getString(6);
                            if(brand[numItemsInbill].length() <= 0)
                            {
                                brand[numItemsInbill] = "menz world";
                            }
                            switch (c3.getInt(7))
                            {
                                case 0:size[numItemsInbill] =  "SMALL";break;
                                case 1: size[numItemsInbill] =  "MEDIUM";break;
                                case 2: size[numItemsInbill] =  "LARGE";break;
                                case 3: size[numItemsInbill] =  "X-LARGE";break;
                                case 4: size[numItemsInbill] =  "XX-LARGE";break;
                                case -1: size[numItemsInbill] = "N A" ; break;
                                default: size[numItemsInbill] =  c3.getString(7);break;
                            }
                            numItemsInbill++;
                            total += c3.getInt(2);
                            TextView txt_total = (TextView) findViewById(R.id.txt_total);
                            txt_total.setText("RS: " + total);
                        }
                    }


                }
            }


        }


        adapter = new
                CustomListMakeBillList(MakeBillActivity.this, itemCode, item, price,brand,size,numItemsInbill);

        billItems = (ListView)findViewById(R.id.list_make_bill);
        billItems.setAdapter(adapter);
        billItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                int stockId = -1;
                if(position > numItemsInbill -1)
                {
                    return;
                }
                Log.d("JKS","Clicked on bill item position "+position + " item="+itemCode[position]+ " price="+price[position]+ " brand="+brand[position]+ " size="+size[position]);
                int itemsSold_q = 0;
                String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE serialNumber="+itemCode[position];
                Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                if(c4.getCount() == 1) {
                    c4.moveToNext();
                    Log.d("JKS", "Items Sold = " + c4.getInt(0));
                    itemsSold_q = c4.getInt(0);
                }
                String updateQuerry = "UPDATE stockData SET itemsSold = "+(itemsSold_q - 1)+" WHERE serialNumber="+itemCode[position];
                Log.d("JKS","update Querry = "+updateQuerry);
                MainActivity.mdb.execSQL(updateQuerry);

                String getStockIdFromSerial = "SELECT stockId FROM stockData WHERE serialNumber="+itemCode[position];
                Cursor getStockId = MainActivity.mdb.rawQuery(getStockIdFromSerial, null);
                Log.d("JKS","Get "+getStockId.getCount());

                if(getStockId.getCount() == 1) {
                    getStockId.moveToNext();
                    Log.d("JKS ", "stock id to be removed is " + getStockId.getInt(0));
                    stockId = getStockId.getInt(0);
                }


                String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE stockRefId="+ stockId;
                Log.d("JKS"," select querry = "+getStockIdQuerry);

                Cursor stockRefId = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
                Log.d("JKS","Get "+stockRefId.getCount());
                int count_refId = stockRefId.getCount();

                insertQuerry = " DELETE FROM billData  WHERE stockRefId ="+stockId;
                MainActivity.mdb.execSQL(insertQuerry);
                insertQuerry ="";

                removedSlNo[removedIndex++] = stockId;

                if(count_refId > 1)
                {
                    for(int i = 0; i <count_refId -1 ; i++) {
                        insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values (" + curBillRefId + "," + stockId + ");";
                        MainActivity.mdb.execSQL(insertQuerry);
                    }
                }

                numItemsInbill--;
                itemCount--;
                for(int i = position; i < numItemsInbill - 1 ; i++)
                {
                    itemCode[i] =  itemCode[i + 1];
                    item[i] = item[i + 1];
                    brand[i]= brand[i + 1];
                    size[i] = size[i + 1];
                    price[i] = price[i + 1];
                }
                adapter.updateCount(numItemsInbill);
                adapter.notifyDataSetChanged();
                billItems.invalidateViews();
                billItems.refreshDrawableState();

            }
        });

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

                int refId = curBillRefId;
                int discount = Integer.parseInt(editDiscount.getText().toString());
                String dateTime = AddItemActivity.getDateTime();

                if(editBillFlag == false) {
                    String billQuerry = "INSERT INTO billTable (refId,discount,billDate) values(" + curBillRefId + "," + discount + ",'" + dateTime + "');";
                    Log.d("JKS", "Bill Query = " + billQuerry);
                    MainActivity.mdb.execSQL(billQuerry);
                }
                else
                {
                    String billQuerry = "UPDATE billTable set billDate='" + dateTime + "' WHERE billId="+billNo+";";
                    Log.d("JKS", "Bill Query = " + billQuerry);
                    MainActivity.mdb.execSQL(billQuerry);
                }

                insertQuerry="";

                int billId = 0;
                if(editBillFlag == false) {
                    String getBillIdQuerry = "SELECT billId FROM billTable WHERE refId="+refId;
                    Log.d("JKS"," select querry = "+getBillIdQuerry);

                    Cursor c = MainActivity.mdb.rawQuery(getBillIdQuerry, null);

                    if(c.getCount() == 1) {
                        c.moveToNext();
                        Log.d("JKS ", "bill id is is " + c.getInt(0));
                        billId = c.getInt(0);
                    }
                }
                else
                {
                    billId = billNo;
                }

                Intent intent = new Intent(MakeBillActivity.this, BillActivity.class);
                intent.putExtra("billno", billId);
                intent.putExtra("date",dateTime);
                intent.putExtra("discount",discount);
                startActivity(intent);
                if(editBillFlag == false) {
                    MainActivity.billRefId++;
                }
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

                cancel_DatasRecorded();

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


                Cursor c3 = MainActivity.mdb.rawQuery("SELECT serialNumber,item,selling_price,stockId,noOfItems,itemsSold,brand,size FROM stockData WHERE serialNumber='" + serialNum + "'", null);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txt_serial.getWindowToken(), 0);


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
                        if(editBillFlag == true)
                        {
                            newItem[newItemIndex++] = c3.getInt(3);
                        }
                        Log.d("JKS", "slNum = " + c3.getString(0) + " item = " + c3.getInt(1) + " price = " + c3.getInt(2)+" stockId ="+c3.getInt(3));
                        insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values ("+curBillRefId+","+c3.getInt(3)+");";
                        MainActivity.mdb.execSQL(insertQuerry);
                        Log.d("JKS", "Ins Query = " + insertQuerry);
                        insertQuerry ="";

                        Log.d("JKS","-- inserting item to billData--");
                        Log.d("JKS ","------Items in this bill----");
                        String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+ curBillRefId;
                        Log.d("JKS"," select querry = "+getStockIdQuerry);

                        Cursor stockRefId = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
                        Log.d("JKS","Get "+stockRefId.getCount());
                        while(stockRefId.moveToNext()) {
                            Log.d("JKS ", "Items in this bill is " + stockRefId.getInt(0));
                        }
                        Log.d("JKS ","--------------------------");

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


                        itemCode[numItemsInbill] = c3.getString(0);

                        switch (c3.getInt(1)) {
                            case 0: item[numItemsInbill] = "SHIRT";break;
                            case 1: item[numItemsInbill] = "JEANS"; break;
                            case 2: item[numItemsInbill] = "OTHERS"; break;
                            case 3: item[numItemsInbill] = "PANTS"; break;
                            case 4: item[numItemsInbill] = "T-SHIRT"; break;
                            case 5: item[numItemsInbill] = "BELT"; break;
                            case 6: item[numItemsInbill] = "INNER"; break;
                            case 7: item[numItemsInbill] = "SHORTS"; break;
                            case 8: item[numItemsInbill] = "WALLET"; break;
                            case 9: item[numItemsInbill] = "OTHERS"; break;
                            default:item[numItemsInbill] = "OTHERS";
                        }

                        price[numItemsInbill] = c3.getString(2);
                        brand[numItemsInbill]= c3.getString(6);
                        if(brand[numItemsInbill].length() <= 0)
                        {
                            brand[numItemsInbill] = "menz world";
                        }
                        switch (c3.getInt(7))
                        {
                            case 0:size[numItemsInbill] =  "SMALL";break;
                            case 1: size[numItemsInbill] =  "MEDIUM";break;
                            case 2: size[numItemsInbill] =  "LARGE";break;
                            case 3: size[numItemsInbill] =  "X-LARGE";break;
                            case 4: size[numItemsInbill] =  "XX-LARGE";break;
                            case -1: size[numItemsInbill] = "N A" ; break;
                            default: size[numItemsInbill] =  c3.getString(7);break;
                        }
                        numItemsInbill++;
                        adapter.updateCount(numItemsInbill);
                        adapter.notifyDataSetChanged();
                        billItems.invalidateViews();
                        billItems.refreshDrawableState();
                        total += c3.getInt(2);
                        TextView txt_total = (TextView) findViewById(R.id.txt_total);
                        txt_total.setText("RS: " + total);

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

                    insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values ("+curBillRefId+","+c3.getInt(3)+");";
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

                    itemCode[numItemsInbill] = c3.getString(0);

                    switch (c3.getInt(1)) {

                        case 0:textview2.setText("SHIRT  "); item[numItemsInbill] = "SHIRT";break;
                        case 1:textview2.setText("JEANS  "); item[numItemsInbill] = "JEANS"; break;
                        case 2:textview2.setText("OTHERS "); item[numItemsInbill] = "OTHERS"; break;
                        case 3:textview2.setText("PANTS  "); item[numItemsInbill] = "PANTS"; break;
                        case 4:textview2.setText("T-SHIRT"); item[numItemsInbill] = "T-SHIRT"; break;
                        case 5:textview2.setText("BELT   "); item[numItemsInbill] = "BELT"; break;
                        case 6:textview2.setText("INNER  "); item[numItemsInbill] = "INNER"; break;
                        case 7:textview2.setText("SHORTS "); item[numItemsInbill] = "SHORTS"; break;
                        case 8:textview2.setText("WALLET "); item[numItemsInbill] = "WALLET"; break;
                        case 9:textview2.setText("OTHERS "); item[numItemsInbill] = "OTHERS"; break;
                        default:textview2.setText("OTHERS ");item[numItemsInbill] = "OTHERS";
                    }

                    textview3.setText(c3.getString(2));
                    price[numItemsInbill] = c3.getString(2);
                    brand[numItemsInbill]= c3.getString(6);
                    if(brand[numItemsInbill].length() <= 0)
                    {
                        brand[numItemsInbill] = "menz world";
                    }
                    switch (c3.getInt(7))
                    {
                        case 0:size[numItemsInbill] =  "SMALL";break;
                        case 1: size[numItemsInbill] =  "MEDIUM";break;
                        case 2: size[numItemsInbill] =  "LARGE";break;
                        case 3: size[numItemsInbill] =  "X-LARGE";break;
                        case 4: size[numItemsInbill] =  "XX-LARGE";break;
                        case -1: size[numItemsInbill] = "N A" ; break;
                        default: size[numItemsInbill] =  c3.getString(7);break;
                    }
                    numItemsInbill++;
                    adapter.updateCount(numItemsInbill);
                    adapter.notifyDataSetChanged();
                    billItems.invalidateViews();
                    billItems.refreshDrawableState();
                    total += c3.getInt(2);
                    TextView txt_total = (TextView) findViewById(R.id.txt_total);
                    txt_total.setText("RS: " + total);


                }
            }


        }else{
          Log.d("JKS","ERRROR in reading barcode");
        }
    }
    public void cancel_DatasRecorded()
    {


        if(editBillFlag == false) {
            String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId=" + curBillRefId;
            Cursor c2 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
            Log.d("JKS", "Get " + c2.getCount());


            if (c2.getCount() != 0) {
                while (c2.moveToNext()) {
                    int itemsSold_q = 0;
                    String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId=" + c2.getInt(0);
                    Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                    if (c4.getCount() == 1) {
                        c4.moveToNext();
                        Log.d("JKS", "Items Sold = " + c4.getInt(0));
                        itemsSold_q = c4.getInt(0);
                    }
                    String updateQuerry = "UPDATE stockData SET itemsSold = " + (itemsSold_q - 1) + " WHERE stockId=" + c2.getInt(0);
                    Log.d("JKS", "update Querry = " + updateQuerry);
                    MainActivity.mdb.execSQL(updateQuerry);
                }
            }

            insertQuerry = " DELETE FROM billData  WHERE billrefId =" + curBillRefId;
            MainActivity.mdb.execSQL(insertQuerry);
            insertQuerry = "";
        }
        else
        {
            Log.d("JKS","Cancel operation in edit bill");

            if(removedIndex > 0)
            {

                for(int i = 0; i<removedIndex; i++) {

                    int itemsSold_q = 0;
                    String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId=" +  removedSlNo[i];
                    Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                    if (c4.getCount() == 1) {
                        c4.moveToNext();
                        Log.d("JKS", "Items Sold = " + c4.getInt(0));
                        itemsSold_q = c4.getInt(0);
                    }
                    String updateQuerry = "UPDATE stockData SET itemsSold = " + (itemsSold_q + 1) + " WHERE stockId=" +  removedSlNo[i];
                    Log.d("JKS", "update Querry = " + updateQuerry);
                    MainActivity.mdb.execSQL(updateQuerry);

                    insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values (" + curBillRefId + "," + removedSlNo[i] + ");";
                    MainActivity.mdb.execSQL(insertQuerry);
                    insertQuerry = "";
                    // update stockid also
                }
            }
            if(newItemIndex > 0)
            {
                for(int i = 0; i<newItemIndex; i++) {
                    int itemsSold_q = 0;
                    String itemSoldQuerry = "SELECT itemsSold FROM stockData WHERE stockId=" + newItem[i];
                    Cursor c4 = MainActivity.mdb.rawQuery(itemSoldQuerry, null);
                    if (c4.getCount() == 1) {
                        c4.moveToNext();
                        Log.d("JKS", "Items Sold = " + c4.getInt(0));
                        itemsSold_q = c4.getInt(0);
                    }
                    String updateQuerry = "UPDATE stockData SET itemsSold = " + (itemsSold_q - 1) + " WHERE stockId=" + newItem[i];
                    Log.d("JKS", "update Querry = " + updateQuerry);
                    MainActivity.mdb.execSQL(updateQuerry);


                    String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE stockRefId="+  newItem[i];
                    Log.d("JKS"," select querry = "+getStockIdQuerry);

                    Cursor stockRefId = MainActivity.mdb.rawQuery(getStockIdQuerry, null);
                    Log.d("JKS","Get "+stockRefId.getCount());
                    int count_refId = stockRefId.getCount();

                    insertQuerry = " DELETE FROM billData  WHERE stockRefId ="+ newItem[i];
                    MainActivity.mdb.execSQL(insertQuerry);
                    insertQuerry ="";

                    if(count_refId > 1)
                    {
                        for(i = 0; i <count_refId -1 ; i++) {
                            insertQuerry = " INSERT INTO billData (billrefId,stockRefId) values (" + curBillRefId + "," +  newItem[i] + ");";
                            MainActivity.mdb.execSQL(insertQuerry);
                        }
                    }
                    insertQuerry = "";
                    // update stockid also
                }
            }
        }

    }
}
