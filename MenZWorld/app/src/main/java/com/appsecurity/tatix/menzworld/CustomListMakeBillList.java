package com.appsecurity.tatix.menzworld;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by jithin suresh on 6/26/2016.
 */
public class CustomListMakeBillList  extends ArrayAdapter<String> {

    private final Activity context;

    private int maxItem;


    private  String[] itemCode;
    private  String[] item;
    private  String[] price;
    private  String[] brand;
    private  String[] size;


    public CustomListMakeBillList(Activity context,
                                  String[] itemCode,
                                  String[] item,
                                  String[] price,
                                  String[] brand,
                                  String[] size,
                                  int noOfItem) {
        super(context, R.layout.list_bill,item);
        this.context = context;
        this.itemCode = itemCode;
        this.item = item;
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.maxItem = noOfItem;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(position>=maxItem) {
            LayoutInflater inflater = context.getLayoutInflater();
            return inflater.inflate(R.layout.list_bill_item_single, null, true);
        }

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_bill_item_single, null, true);
        TextView txt_itemCode = (TextView) rowView.findViewById(R.id.txt_bill_single_item_code);
        TextView txt_price = (TextView) rowView.findViewById(R.id.txt_bill_single_item_price);
        TextView txt_item = (TextView) rowView.findViewById(R.id.txt_bill_single_item);
        TextView txt_brand = (TextView) rowView.findViewById(R.id.txt_bill_single_item_brand);
        TextView txt_size = (TextView) rowView.findViewById(R.id.txt_bill_single_item_size);

        if(position == 0) {
            txt_itemCode.setTypeface(null, Typeface.BOLD);
            txt_price.setTypeface(null, Typeface.BOLD);
            txt_item.setTypeface(null, Typeface.BOLD);
            txt_brand.setTypeface(null, Typeface.BOLD);
            txt_size.setTypeface(null, Typeface.BOLD);
        }
        txt_itemCode.setText(itemCode[position]);
        txt_price.setText(price[position]);
        txt_item.setText(item[position]);
        txt_brand.setText(brand[position]);
        txt_size.setText(size[position]);


        return rowView;
    }

    void updateAll(String[] itemCode,
                   String[] item,
                   String[] price,
                   String[] brand,
                   String[] size,
                   int noOfItem) {
        this.itemCode = itemCode;
        this.item = item;
        this.price = price;
        this.brand = brand;
        this.size = size;
        this.maxItem = noOfItem;
    }
    void updateCount(int count) {

        this.maxItem = count;
    }
}