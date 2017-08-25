package com.ttx.photopuzzle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static java.sql.Types.NULL;

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
            viewHolder.tv2=(TextView)view.findViewById(R.id.tv_Puzzle);
            viewHolder.iv1=(ImageView)view.findViewById(R.id.imagelogo);
            viewHolder.imgstatus=(ImageView) view.findViewById(R.id.imv_status);

            view.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }



        // status changed puzzle open,closed


       /*int j = 0;*/


        if (list1.get(i).getStatus()==0)
        {
            viewHolder.  imgstatus.setImageResource(R.drawable.status_locked);


        }
        else if (list1.get(i).getStatus()==1)
        {
            viewHolder.  imgstatus.setImageResource(R.drawable.status_open);
        }
        else if (list1.get(i).getStatus()==2)
        {
            viewHolder.  imgstatus.setImageResource(R.drawable.status_done);
        }



        viewHolder.tv1.setText(" "+list1.get(i).getNo());

        // viewHolder.tv2.setText(" "+list1.get(i).getStatus());

        return view;

    }

  


    class  ViewHolder{
       TextView tv1;
        TextView tv2;
        ImageView iv1;
        ImageView imgstatus;
    }

}
