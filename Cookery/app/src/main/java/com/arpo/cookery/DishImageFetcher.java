package com.arpo.cookery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.RelativeLayout;

import java.net.URL;
import java.util.List;

/**
 * Created by jithin on 10/9/18.
 */

public class DishImageFetcher implements Runnable
{
    String url;
    RelativeLayout layout;
    Context ctx;
    List<ListItemDishes> dishList;
    int position;

    public DishImageFetcher(String url, RelativeLayout rel, Context context)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.position = -1;
    }
    public DishImageFetcher(String url, RelativeLayout rel, Context context, List<ListItemDishes> list, int position)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.dishList = list;
        this.position = position;
    }
    public void run()
    {
        DishImageSetterUI imgSetRunnable;
        try {
            URL url = new URL(this.url);
            Bitmap myImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            if(this.position != -1) {

                Drawable dr = new BitmapDrawable(myImage);
                this.dishList.get(this.position).setPreviewImg(myImage);
                this.dishList.get(this.position).setPreviewSet(1);
                Log.d("JKS","Setting preview image for "+this.dishList.get(this.position).getName());
            }

            imgSetRunnable = new DishImageSetterUI(myImage, this.layout);
            ((Activity) ctx).runOnUiThread(imgSetRunnable);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}