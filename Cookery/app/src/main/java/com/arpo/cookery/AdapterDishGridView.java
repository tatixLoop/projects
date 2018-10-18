package com.arpo.cookery;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jithin on 10/9/18.
 */

public class AdapterDishGridView  extends BaseAdapter {
    List<ListItemDishes> list;
    Context context;

    AdapterDishGridView(Context ctx, List<ListItemDishes> l) {
        list = l;
        context = ctx;
    }

    void print(String str)
    {
        Log.d("JKS",str);
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
                    inflate(R.layout.gv_dishes_list_layout, parent, false);

            RelativeLayout rel_dishbox = convertView.findViewById(R.id.rel_dishbox);
            rel_dishbox.setBackgroundColor(Color.parseColor("#c4bebe"));
            TextView calory = convertView.findViewById(R.id.txt_calgv);
            TextView cukTime = convertView.findViewById(R.id.txt_cooktimegv);
            //rel_dishbox.setBackgroundColor(Color.BLACK);

            final ListItemDishes item = list.get(position);



            String box_preview_url = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    item.getImg_path() + "/box_preview.jpg";

            //print("Setting "+box_preview_url);
            print("Set image of "+item.getName() + "Id "+item.getId() + " from"+item.getImg_path() );
            print("Position = "+position);
            TextView recipeName = convertView.findViewById(R.id.txt_recipeName);
            recipeName.setText(item.getName()+"");

            int seconds = item.getCooktimeinsec();
            int mins = seconds/60;

            cukTime.setText(mins+" Mins");
            calory.setText(item.getCalory()+ "Cal");



            Runnable imgFetch = new DishImageFetcher(box_preview_url,rel_dishbox, context);
            new Thread(imgFetch).start();

        }
        return convertView;
    }
}
