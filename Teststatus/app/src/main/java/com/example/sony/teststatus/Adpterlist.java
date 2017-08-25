package com.example.sony.teststatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SONY on 19-07-2017.
 */

public class Adpterlist extends BaseAdapter {

    Context context;
    List<listcls> list1;


    public Adpterlist(Context context, List<listcls> list1) {
        this.context = context;
        this.list1 = list1;

    }
    @Override
    public int getCount() {
        return 0;
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
            viewHolder.tv2=(TextView)view.findViewById(R.id.tv_Puzzle);
            viewHolder.iv1=(ImageView)view.findViewById(R.id.imagelogo);
            viewHolder.imgstatus=(ImageView) view.findViewById(R.id.imv_status);

            view.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }



        ///// status changed puzzle open,closed


        int status =0;

        if (status==0)
        {
            viewHolder.imgstatus.setImageDrawable(getResources().getDrawable(R.drawable.status_locked));

        }
        else if (status==1)
        {
            viewHolder.imgstatus.setImageDrawable(getResources().getDrawable(R.drawable.status_open));

        }
        else if (status==2)
        {
            viewHolder.imgstatus.setImageDrawable(getResources().getDrawable(R.drawable.status_done));
        }



        viewHolder.tv1.setText(" "+list1.get(i).getNo());

        return view;
    }

    class  ViewHolder{
        TextView tv1;
        TextView tv2;
        ImageView iv1;
        ImageView imgstatus;
    }
}
