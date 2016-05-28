package com.appsecurity.tatix.menzworld;

/**
 * Created by jithin on 5/25/2016.
 */
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customListAll extends ArrayAdapter<String>{

    private final Activity context;

    private int maxItem;

    private final String[] serialNo;
    private String[] itemType;
    private String[] price;
    private String[] stockNoOfItem;


    public customListAll(Activity context,
                      String[] serialNo,  String[] itemType, String[] price, String[] stockNoOfItem, int noOfItem) {
        super(context, R.layout.list_all_single, serialNo);
        this.context = context;
        this.serialNo = serialNo;
        this.itemType = itemType;
        this.price = price;
        this.stockNoOfItem = stockNoOfItem;
        this.maxItem = noOfItem;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(position>=maxItem) {
            LayoutInflater inflater = context.getLayoutInflater();
            return inflater.inflate(R.layout.list_all_single, null, true);
        }

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_all_single, null, true);
        TextView slno = (TextView) rowView.findViewById(R.id.slNo);
        TextView item = (TextView) rowView.findViewById(R.id.item);
        TextView txtPrice = (TextView) rowView.findViewById(R.id.price);
        TextView noOfItems = (TextView) rowView.findViewById(R.id.noOfItems);

        slno.setText(serialNo[position]);
        item.setText(itemType[position]);
        txtPrice.setText(price[position]);
        noOfItems.setText(stockNoOfItem[position]);


        return rowView;
    }
}