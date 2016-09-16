package com.example.sony.pushupchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SONY on 09-09-2016.
 */
public class Adapterpushuplist extends BaseAdapter {

    Context context;
    List<pushupc> list1;
    //List<Integer> img;

    public Adapterpushuplist(Context context, List<pushupc> list1){
        this.context=context;
       this.list1=list1;
        //this.list1=list1;
        //this.img=img;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.customlistviewexcersises, null, false);
            viewHolder.tv1=(TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.img=(ImageView)convertView.findViewById(R.id.img);

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.img.setImageResource(R.mipmap.ic_launcher);
        viewHolder.tv1.setText(list1.get(position).getName());
        return convertView;
    }

    class  ViewHolder{
        ImageView img;
        TextView tv1;
    }

}
