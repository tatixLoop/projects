package com.appsecurity.tatix.menzworld;

/**
 * Created by jithin on 5/25/2016.
 */
import android.app.Activity;
import android.graphics.Typeface;
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
    private String[] sellingPrice;
    private String[] soldItems;
    private String[] brand;
    private String[] size;


    public customListAll(Activity context,
                      String[] serialNo,  String[] itemType, String[] price, String[] stockNoOfItem,String[] sellingPrice, String[] soldItems, String[] brand, String[] size, int noOfItem) {
        super(context, R.layout.list_all_single, serialNo);
        this.context = context;
        this.serialNo = serialNo;
        this.itemType = itemType;
        this.price = price;
        this.stockNoOfItem = stockNoOfItem;
        this.sellingPrice = sellingPrice;
        this.soldItems = soldItems;
        this.maxItem = noOfItem;
        this.brand = brand;
        this.size = size;

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
        TextView txt_sellingPrice = (TextView) rowView.findViewById(R.id.selling_rpice);
        TextView txtsoldItems = (TextView) rowView.findViewById(R.id.soldItems);
        TextView txt_brand = (TextView)rowView.findViewById(R.id.brnd);
        TextView txt_size = (TextView) rowView.findViewById(R.id.sz);

        if(position == 0)
        {
            slno.setTypeface(null, Typeface.BOLD);
            item.setTypeface(null, Typeface.BOLD);
            txtPrice.setTypeface(null, Typeface.BOLD);
            noOfItems.setTypeface(null, Typeface.BOLD);
            txt_sellingPrice.setTypeface(null, Typeface.BOLD);
            txtsoldItems.setTypeface(null, Typeface.BOLD);
            txt_brand.setTypeface(null, Typeface.BOLD);
            txt_size.setTypeface(null, Typeface.BOLD);
        }

        slno.setText(serialNo[position]);
        item.setText(itemType[position]);
        txtPrice.setText(price[position]);
        noOfItems.setText("   "+stockNoOfItem[position]);
        txt_sellingPrice.setText(sellingPrice[position]);
        txtsoldItems.setText("   "+soldItems[position]);
        txt_brand.setText(brand[position]);
        txt_size.setText(size[position]);


        return rowView;
    }
}