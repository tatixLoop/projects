package com.arpo.cookery;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by jithin on 10/9/18.
 */

public class AdapterDishGridView  extends RecyclerView.Adapter<AdapterDishGridView.CookeryViewHolder>  {
    List<ListItemDishes> list;
    Context context;
    protected ItemListener mListener;



    AdapterDishGridView(Context ctx, List<ListItemDishes> l) {
        list = l;
        context = ctx;
    }

    AdapterDishGridView(Context ctx, List<ListItemDishes> l, ItemListener itemListener) {
        list = l;
        context = ctx;
        mListener = itemListener;
    }


    void print(String str)
    {
        Log.d("JKS",str);
    }

    @Override
    public void onBindViewHolder(CookeryViewHolder Vholder, int position) {
        ListItemDishes data = list.get(position);
        Vholder.item = data;

        Vholder.setData(data, position);

    }
    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public CookeryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.gv_dishes_list_layout, parent, false);

        return new CookeryViewHolder(view);
    }

    public class CookeryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RelativeLayout rel_dishbox ;
        public TextView calory ;
        public TextView cukTime ;
        public TextView recipeName ;
        public ListItemDishes item;
        public RelativeLayout details;
        //public TextView author;
        //public TextView rating;

        public CookeryViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            rel_dishbox = v.findViewById(R.id.rel_dishbox);
            calory = v.findViewById(R.id.txt_calgv);
            cukTime = v.findViewById(R.id.txt_cooktimegv);
            recipeName = v.findViewById(R.id.txt_recipeName);
            details = v.findViewById(R.id.rel_details);
            //author = v.findViewById(R.id.txt_author_gi);
            //rating = v.findViewById(R.id.txt_rating_gi);
        }

        public void setData(ListItemDishes item, int position) {
            this.item = item;

            rel_dishbox.setBackgroundColor(Color.parseColor("#c4bebe"));
            String box_preview_url = Globals.host + Globals.appdir + Globals.img_path + "/" +
                    item.getImg_path() + "/box_preview.jpg";

            //print("Setting "+box_preview_url);
            //print("Set image of "+item.getName() + "Id "+item.getId() + " from"+item.getImg_path() );


            recipeName.setText(item.getName()+"");

            int seconds = item.getCooktimeinsec();
            int mins = seconds/60;

            cukTime.setText(mins+" Mins");
            calory.setText(item.getCalory()+ "Cal");
            //author.setText(item.getAuthor());
            //rating.setText(""+(float)item.getRating()/2);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    String.format(Locale.US, "fonts/%s", "font.ttf"));

            recipeName.setTypeface(typeface);
            cukTime.setTypeface(typeface);
            calory.setTypeface(typeface);
            //author.setTypeface(typeface);
            //rating.setTypeface(typeface);

            if(list.get(position).getPreviewSet() == 1)
            {
                Drawable dr = new BitmapDrawable(list.get(position).getPreviewImg());
                rel_dishbox.setBackground(dr);

                int width = list.get(position).getPreviewImg().getWidth();
                int height = list.get(position).getPreviewImg().getHeight();

                Resources r = context.getResources();
                float px = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        170,
                        r.getDisplayMetrics()
                );
                float newHeight = px * height / width;
                rel_dishbox.getLayoutParams().height = (int) newHeight;
                details.setPadding(0, (int)newHeight, 0, 0);


            }
            else {

                Runnable imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISH, item.getId(), box_preview_url, rel_dishbox, details, context, list, position, true);
                //Runnable imgFetch = new DishImageFetcher(box_preview_url, rel_dishbox, context);
                new Thread(imgFetch).start();


            }

        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {

                mListener.onItemClick(item);
            }
        }
    }

    public interface ItemListener {

        void onItemClick(ListItemDishes item);
    }




    @Override
    public long getItemId(int position) {
        return position;
    }
/*
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
    }*/
}
