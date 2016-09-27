package com.appstory.aarppo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jithin suresh on 27-09-2016.
 */
public class AdapterMatchList extends BaseAdapter {

    Context context;
    List<MatchList> list1;
    //List<Integer> img;

    public AdapterMatchList(Context context, List<MatchList> list1){
        this.context=context;
        this.list1=list1;
        //this.list1=list1;
        //this.img=img;
    }


    @Override
    public int getCount() {
        return list1.size();
    }  /// define the string  size else nothing is dsplayed

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
            convertView= LayoutInflater.from(context).inflate(R.layout.custom_list_match_schedule, null, false);
            viewHolder.tv1=(TextView)convertView.findViewById(R.id.tv_name);
            viewHolder.img=(ImageView)convertView.findViewById(R.id.img);
            viewHolder.team1=(TextView)convertView.findViewById(R.id.team1);
            viewHolder.team2=(TextView) convertView.findViewById(R.id.team2);
            viewHolder.place=(TextView) convertView.findViewById(R.id.txt_ground);

            viewHolder.team1.setText(list1.get(position).getTeam1());
            viewHolder.team2.setText(list1.get(position).getTeam2());
            viewHolder.place.setText(list1.get(position).getLocation());

            convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.img.setImageResource(list1.get(position).getImg());
        viewHolder.tv1.setText(list1.get(position).getName());
        viewHolder.team1.setText(list1.get(position).getTeam1());
        viewHolder.team2.setText(list1.get(position).getTeam2());
        viewHolder.place.setText(list1.get(position).getLocation());
        return convertView;
    }

    class  ViewHolder{
        ImageView img;
        TextView tv1;
        TextView team1;
        TextView team2;
        TextView place;
    }

}
