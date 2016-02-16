package com.jaapps.lockengine;

/**
 * Created by jithin on 24/1/16.
 */
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class customList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] apps;
    private final Integer[] imageId;
    private Drawable[] drawArray = null;
    private Integer[] lockStatus;
    private int maxItem;

    public customList(Activity context,
                      String[] apps, Integer[] imageId) {
        super(context, R.layout.list_single, apps);
        this.context = context;
        this.apps = apps;
        this.imageId = imageId;

    }
    public customList(Activity context,
                      String[] apps, Integer[] imageId,Drawable[] drawId) {
        super(context, R.layout.list_single, apps);
        this.context = context;
        this.apps = apps;
        this.imageId = imageId;
        this.drawArray = drawId;

    }
    public customList(Activity context,
                      String[] apps, Integer[] imageId,Drawable[] drawId,Integer[] lockStatus) {
        super(context, R.layout.list_single, apps);
        this.context = context;
        this.apps = apps;
        this.imageId = imageId;
        this.drawArray = drawId;
        this.lockStatus = lockStatus;

    }
    public customList(Activity context,
                      String[] apps, Integer[] imageId,Drawable[] drawId,Integer[] lockStatus, int noOfItem) {
        super(context, R.layout.list_single, apps);
        this.context = context;
        this.apps = apps;
        this.imageId = imageId;
        this.drawArray = drawId;
        this.lockStatus = lockStatus;
        this.maxItem = noOfItem;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(position>=maxItem) {
            LayoutInflater inflater = context.getLayoutInflater();
            return inflater.inflate(R.layout.list_single, null, true);
        }

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        ImageView img_lockState = (ImageView) rowView.findViewById(R.id.img_lock_state);
        txtTitle.setText(apps[position]);


        //imageView.setImageResource(imageId[position]);
        imageView.setImageDrawable(drawArray[position]);
        if(lockStatus[position] == 0)
            img_lockState.setImageResource(R.drawable.unlock_state);
        else
            img_lockState.setImageResource(R.drawable.lock_state);
        return rowView;
    }
}