package com.jaapps.drinkrecipes;

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

public class AdapterStepListView extends BaseAdapter {
    List<ListItemSteps> list;
    Context context;

    AdapterStepListView(Context ctx, List<ListItemSteps> l) {
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
                    inflate(R.layout.layout_steplist, parent, false);

            RelativeLayout rel_dishbox = convertView.findViewById(R.id.rel_dishbox);
            //rel_dishbox.setBackgroundColor(Color.BLACK);

            final ListItemSteps item = list.get(position);

            TextView txt_stepNo = convertView.findViewById(R.id.txt_stepno);
            TextView txt_steps = convertView.findViewById(R.id.txt_steps);

            txt_stepNo.setText("Step "+item.getStepNo()+"");
            txt_steps.setText(item.getStep());

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    String.format(Locale.US, "fonts/%s", "font.ttf"));

            txt_stepNo.setTypeface(typeface);
            txt_steps.setTypeface(typeface);


        }
        return convertView;
    }
}
