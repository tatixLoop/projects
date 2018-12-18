package com.arpo.cookery;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

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
        if (list != null) {
            return list.size(); //returns total of items in the list
        }
        else
        {
            return 0;
        }
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

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    String.format(Locale.US, "fonts/%s", "font.ttf"));

            ((TextView) convertView.findViewById(R.id.txt_qty)).setTypeface(typeface);
            txt_ingredient.setTypeface(typeface);


            final RelativeLayout checkBox = convertView.findViewById(R.id.chkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.selected == 0) {
                        item.selected = 1;
                        checkBox.setBackground(context.getResources().getDrawable(R.drawable.laout_bg_green));
                    }
                    else
                    {
                        checkBox.setBackground(context.getResources().getDrawable(R.drawable.red_circle));
                        item.selected = 0;
                    }

                }
            });

        }
        return convertView;
    }
}