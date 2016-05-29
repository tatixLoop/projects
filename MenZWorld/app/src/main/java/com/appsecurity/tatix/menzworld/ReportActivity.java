package com.appsecurity.tatix.menzworld;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    private Calendar cal;
    private int day;
    private int month;
    private int year;

    String strFromDate;
    String strToDate;
    TextView txtFrmDate;
    TextView txtToDate;

    TextView head_1;
    TextView head_2;
    TextView head_3;
    TextView head_4;
    TextView head_5;

    Boolean flagSelFromDate = false;
    Boolean getFlagSelToDate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        txtToDate = (TextView)findViewById(R.id.txt_toDate);
        txtFrmDate = (TextView)findViewById(R.id.txt_fromDate);

        TextView txtitems = (TextView)findViewById(R.id.txt_report_noItems);
        TextView txtCost = (TextView)findViewById(R.id.txt_report_totalSell);
        TextView txtActual = (TextView)findViewById(R.id.txt_reports_costOfProducts);
        TextView txtProfit = (TextView)findViewById(R.id.txt_report_profit);
        TextView txtDiscount = (TextView)findViewById(R.id.txt_reports_discounts);

        head_1 = (TextView)findViewById(R.id.textView22);
        head_2 = (TextView)findViewById(R.id.textView23);
        head_3 = (TextView)findViewById(R.id.textView24);
        head_4 = (TextView)findViewById(R.id.textView25);
        head_5 = (TextView)findViewById(R.id.textView28);

        head_1.setVisibility(View.GONE);
        head_2.setVisibility(View.GONE);
        head_3.setVisibility(View.GONE);
        head_4.setVisibility(View.GONE);
        head_5.setVisibility(View.GONE);


        txtitems.setText("");
        txtCost.setText("");
        txtActual.setText("");
        txtProfit.setText("");
        txtToDate.setText("");
        txtFrmDate.setText("");
        txtDiscount.setText("");

        Button btnFrom = (Button)findViewById(R.id.btn_fromDate);
        Button btnTo = (Button)findViewById(R.id.btn_toDate);
        Button btnView = (Button)findViewById(R.id.btn_view_report);

        btnFrom.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDialog(0);
                    }
                });
        btnTo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(1);

                    }
                });
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!flagSelFromDate || !getFlagSelToDate) {
                            Log.d("JKS","Please set the dates properly");
                            return;
                        }
                        String querry = "SELECT * FROM billTable WHERE billDate >= Datetime('"+strFromDate+"') AND billDate <= Datetime('"+strToDate+"')";

                        Log.d("JKS","Querry = "+querry);

                        Cursor c2 = MainActivity.mdb.rawQuery(querry, null);
                        Log.d("JKS", "Get " + c2.getCount());

                        int totalItems = 0;
                        int totalDiscount = 0;
                        int totalMoneyReceived = 0;
                        int totalActualCost = 0;
                        int totalProfit = 0;
                        TableLayout tl = (TableLayout) findViewById(R.id.tbl_report);
                        tl.removeAllViews();
                        if(c2.getCount() != 0) {
                            head_1.setVisibility(View.VISIBLE);
                            head_2.setVisibility(View.VISIBLE);
                            head_3.setVisibility(View.VISIBLE);
                            head_4.setVisibility(View.VISIBLE);
                            head_5.setVisibility(View.VISIBLE);

                            while (c2.moveToNext()) {
                                Log.d("JKS","Data billid= "+c2.getString(0)+" refId ="+c2.getString(1)+" discount ="+c2.getString(2)+" date = "+c2.getString(3));
                                totalDiscount += Integer.parseInt(c2.getString(2));
                                String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+c2.getString(1);
                                Log.d("JKS","GetStockIdQuerry = "+getStockIdQuerry);
                                Cursor c3 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);

                                TableRow tr1 = new TableRow(ReportActivity.this);
                                TextView textview = new TextView(ReportActivity.this);
                                textview.setText("Bill id:  "+c2.getString(0));
                                tr1.addView(textview);
                                tl.addView(tr1, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                if(c3.getCount() != 0)
                                {
                                    totalItems += c3.getCount();
                                    while (c3.moveToNext()) {
                                        Log.d("JKS","StockId = "+c3.getString(0));
                                        String getProductDataQuerry = "SELECT serialNumber,item,price,selling_price FROM stockData WHERE stockId="+c3.getString(0);
                                        Log.d("JKS","getProductDataQuerry = "+getProductDataQuerry);
                                        Cursor c4 = MainActivity.mdb.rawQuery(getProductDataQuerry, null);

                                        TableRow tr3 = new TableRow(ReportActivity.this);
                                        TextView textview4 = new TextView(ReportActivity.this);
                                        textview4.setText(" ");
                                        tr3.addView(textview4);
                                        tl.addView(tr3, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                        if(c4.getCount() != 0)
                                        {
                                            while (c4.moveToNext()) {
                                                Log.d("JKS","slno= "+c4.getString(0)+ " item ="+c4.getString(1)+ " price ="+c4.getString(2)+" sel price ="+c4.getString(3));
                                                totalMoneyReceived += Integer.parseInt(c4.getString(3));
                                                totalActualCost += Integer.parseInt(c4.getString(2));

                                                TableRow tr2 = new TableRow(ReportActivity.this);
                                                TextView textview1 = new TextView(ReportActivity.this);
                                                TextView textview2 = new TextView(ReportActivity.this);
                                                TextView textview3 = new TextView(ReportActivity.this);
                                                textview1.setText(c4.getString(0));
                                                switch (c4.getInt(1)) {
                                                    case 0:textview2.setText("SHIRT"); break;
                                                    case 1:textview2.setText("JEANS");break;
                                                    case 2:textview2.setText("OTHERS");break;
                                                    case 3:textview2.setText("PANTS");break;
                                                    case 4:textview2.setText("T-SHIRT");break;
                                                    case 5:textview2.setText("BELT");break;
                                                    case 6:textview2.setText("INNER");break;
                                                    case 7:textview2.setText("SHORTS");break;
                                                    case 8:textview2.setText("WALLET");break;
                                                    case 9:textview2.setText("OTHERS");break;
                                                    default:textview2.setText("OTHERS");
                                                }
                                                textview3.setText(c4.getString(3));
                                                tr2.addView(textview1);
                                                tr2.addView(textview2);
                                                tr2.addView(textview3);
                                                tl.addView(tr2, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                            }
                                            TableRow tr4 = new TableRow(ReportActivity.this);
                                            TextView textview5 = new TextView(ReportActivity.this);
                                            textview5.setText(" ");
                                            tr4.addView(textview5);
                                            tl.addView(tr4, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                        }
                                    }
                                }
                            }
                        }
                        TextView txtitems = (TextView)findViewById(R.id.txt_report_noItems);
                        TextView txtCost = (TextView)findViewById(R.id.txt_report_totalSell);
                        TextView txtActual = (TextView)findViewById(R.id.txt_reports_costOfProducts);
                        TextView txtProfit = (TextView)findViewById(R.id.txt_report_profit);
                        TextView txtDiscount = (TextView)findViewById(R.id.txt_reports_discounts);

                        txtitems.setText(""+totalItems);
                        txtCost.setText(""+totalMoneyReceived);
                        txtActual.setText(""+totalActualCost);
                        txtDiscount.setText(""+totalDiscount);
                        txtProfit.setText(""+(totalMoneyReceived-totalActualCost-totalDiscount));
                        Log.d("JKS","TotalItems sold = " + totalItems + " Total Money Received = "+totalMoneyReceived+
                                " Total ACtualCost = " +totalActualCost + " Discount = "+totalDiscount+" Profit = "+(totalMoneyReceived-totalActualCost-totalDiscount));
                    }
                });

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        if (id == 0) {

            Log.d("JKS", "Set from date");
            return new DatePickerDialog(this, dateFromPickerListener, year, month, day);
        } else {
            Log.d("JKS", "Set to date");
            return new DatePickerDialog(this, dateToPickerListener, year, month, day);
        }
    }

    private DatePickerDialog.OnDateSetListener dateFromPickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Log.d("JKS", "" + (selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear));

            if(selectedMonth+1 <10)
            {
                if(selectedDay <10)
                    strFromDate = selectedYear + "-0" + (selectedMonth + 1) + "-0" + selectedDay + " 00:00:00";
                else
                    strFromDate = selectedYear + "-0" + (selectedMonth + 1) + "-" + selectedDay + " 00:00:00";

            }
            else {
                if(selectedDay <10)
                    strFromDate = selectedYear + "-" + (selectedMonth + 1) + "-0" + selectedDay + " 00:00:00";
                else
                    strFromDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay + " 00:00:00";
            }
            txtFrmDate.setText(strFromDate);
            flagSelFromDate = true;
        }
    };

    private DatePickerDialog.OnDateSetListener dateToPickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            Log.d("JKS", "" + (selectedDay + " / " + (selectedMonth + 1) + " / "
                    + selectedYear));
            if(selectedMonth+1 <10)
            {
                if(selectedDay<10)
                strToDate = selectedYear + "-0" + (selectedMonth + 1) + "-0" + selectedDay + " 23:59:59";
                else
                    strToDate = selectedYear + "-0" + (selectedMonth + 1) + "-" + selectedDay + " 23:59:59";
            }
                else
            {
                if(selectedDay<10)
                    strToDate = selectedYear + "-" + (selectedMonth + 1) + "-0" + selectedDay + " 23:59:59";
                else
                    strToDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay + " 23:59:59";
            }
            txtToDate.setText(strToDate);
            getFlagSelToDate = true;
        }
    };
}
