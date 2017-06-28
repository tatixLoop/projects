package com.ttx.photopuzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SONY on 27-06-2017.
 */

public class Adapter_PhotoPuzzlelist extends BaseAdapter {


    Context context;
    List<PhotoPuzzleListcls> list1;

    public Adapter_PhotoPuzzlelist(Context context, List<PhotoPuzzleListcls> list1) {
        this.context = context;
        this.list1 = list1;

    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.custom_photopuzzlelist, null, false);
            viewHolder.tv1=(TextView)view.findViewById(R.id.tv_listno);
            viewHolder.tv2=(TextView)view.findViewById(R.id.tv_unlock);

            view.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }


        viewHolder.tv1.setText(" "+list1.get(i).getNo());
        viewHolder.tv2.setText(" "+list1.get(i).getStatus());


        return view;

    }
    class  ViewHolder{
       TextView tv1;
        TextView tv2;
    }

}
