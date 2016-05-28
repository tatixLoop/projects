package com.appsecurity.tatix.menzworld;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by jithin on 5/27/2016.
 */


public class CustomListBill extends ArrayAdapter<String> {

    private final Activity context;

    private int maxItem;


    private final String[] date;
    private final String[] bill;


    public CustomListBill(Activity context,
                          String[] date,  String[] bill,  int noOfItem) {
        super(context, R.layout.list_bill,date);
        this.context = context;
        this.date = date;
        this.bill = bill;
        this.maxItem = noOfItem;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(position>=maxItem) {
            LayoutInflater inflater = context.getLayoutInflater();
            return inflater.inflate(R.layout.list_bill, null, true);
        }

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_bill, null, true);
        TextView txt_billdate = (TextView) rowView.findViewById(R.id.txt_vbilldate);
        TextView txt_billNo = (TextView) rowView.findViewById(R.id.txt_vbillNo);

        txt_billdate.setText(date[position]);
        txt_billNo.setText(bill[position]);


        return rowView;
    }
}