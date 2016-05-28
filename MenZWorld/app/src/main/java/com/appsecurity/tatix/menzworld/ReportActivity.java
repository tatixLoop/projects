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

                        if(c2.getCount() != 0) {
                            while (c2.moveToNext()) {
                                Log.d("JKS","Data billid= "+c2.getString(0)+" refId ="+c2.getString(1)+" discount ="+c2.getString(2)+" date = "+c2.getString(3));
                                totalDiscount += Integer.parseInt(c2.getString(2));
                                String getStockIdQuerry = "SELECT stockRefId FROM billData WHERE billrefId="+c2.getString(1);
                                Log.d("JKS","GetStockIdQuerry = "+getStockIdQuerry);
                                Cursor c3 = MainActivity.mdb.rawQuery(getStockIdQuerry, null);

                                if(c3.getCount() != 0)
                                {
                                    totalItems += c3.getCount();
                                    while (c3.moveToNext()) {
                                        Log.d("JKS","StockId = "+c3.getString(0));
                                        String getProductDataQuerry = "SELECT serialNumber,item,price,selling_price FROM stockData WHERE stockId="+c3.getString(0);
                                        Log.d("JKS","getProductDataQuerry = "+getProductDataQuerry);
                                        Cursor c4 = MainActivity.mdb.rawQuery(getProductDataQuerry, null);

                                        if(c4.getCount() != 0)
                                        {
                                            while (c4.moveToNext()) {
                                                Log.d("JKS","slno= "+c4.getString(0)+ " item ="+c4.getString(1)+ " price ="+c4.getString(2)+" sel price ="+c4.getString(3));
                                                totalMoneyReceived += Integer.parseInt(c4.getString(3));
                                                totalActualCost += Integer.parseInt(c4.getString(2));
                                            }
                                        }
                                    }
                                }
                            }
                        }
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
                strFromDate = selectedYear + "-0" + (selectedMonth + 1) + "-" + selectedDay + " 00:00:00";

            }
            else {
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
                strToDate = selectedYear + "-0" + (selectedMonth + 1) + "-" + selectedDay + " 23:59:59";
            }
                else
            {
                strToDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay + " 23:59:59";
            }
            txtToDate.setText(strToDate);
            getFlagSelToDate = true;
        }
    };
}
