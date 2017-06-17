package com.ttx.photopuzzle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ttx.photopuzzle.ListPuzzleData;

import java.util.List;

/**
 * Created by jithin suresh on 03-06-2017.
 */

public class PuzzleGridData extends BaseAdapter {

    List<ListPuzzleData> puzzleList;
    Context context;

    PuzzleGridData(Context C, List<ListPuzzleData> list) {

        context = C;
        puzzleList = list;
    }

    @Override
    public int getCount() {
        return puzzleList.size();
    }

    @Override
    public Object getItem(int position) {

        return puzzleList.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.singlepuzzleitem, null, false);

            viewHolder.data = (TextView)convertView.findViewById(R.id.txt_text);
            viewHolder.img = (ImageView)convertView.findViewById(R.id.img_peice);
            viewHolder.layout = (RelativeLayout)convertView.findViewById(R.id.layout);



            viewHolder.data.setWidth(puzzleList.get(position).getWidth());
            viewHolder.data.setHeight(puzzleList.get(position).getHeight());

            viewHolder.img.getLayoutParams().width = puzzleList.get(position).getWidth();
            viewHolder.img.getLayoutParams().height = puzzleList.get(position).getHeight();

            viewHolder.layout.getLayoutParams().width = puzzleList.get(position).getWidth();
            viewHolder.layout.getLayoutParams().height = puzzleList.get(position).getHeight();

            /*Typeface font = Typeface.createFromAsset(context.getAssets(), "Tahoma.ttf");

            viewHolder.data .setTypeface(font);

            */


            viewHolder.data.setText(puzzleList.get(position).getText());
            viewHolder.img.setImageBitmap(puzzleList.get(position).getImg());
              convertView.setTag(viewHolder);

        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.data.setText(puzzleList.get(position).getText());
        viewHolder.img.setImageBitmap(puzzleList.get(position).getImg());
        convertView.setBackgroundColor(Color.parseColor("#25776a"));


        return convertView;
    }
    class  ViewHolder{
        TextView data;
        ImageView img;
        RelativeLayout layout;
    }
}