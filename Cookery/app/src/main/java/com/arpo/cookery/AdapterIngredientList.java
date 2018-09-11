package com.arpo.cookery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jithin on 11/9/18.
 */

public class AdapterIngredientList extends BaseAdapter {
    List<ListItemIngredients> list;
    Context context;

    AdapterIngredientList(Context ctx, List<ListItemIngredients> l) {
        list = l;
        context = ctx;
    }

    void print(String str)
    {
        //Log.d("JKS",str);
    }

    @Override
    public int getCount() {
        return list.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return list.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_ingredientlist, parent, false);

            final ListItemIngredients item = list.get(position);

            TextView txt_ingredient = convertView.findViewById(R.id.txt_ingredient);
            txt_ingredient.setText(item.getIngredient());


        }
        return convertView;
    }
}