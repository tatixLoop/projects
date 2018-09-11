package com.arpo.cookery;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RelativeLayout;

import java.net.URL;

/**
 * Created by jithin on 10/9/18.
 */

public class DishImageFetcher implements Runnable
{
    String url;
    RelativeLayout layout;
    Context ctx;

    public DishImageFetcher(String url, RelativeLayout rel, Context context)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
    }
    public void run()
    {
        try {
            URL url = new URL(this.url);
            Bitmap myImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            DishImageSetterUI imgSetRunnable = new DishImageSetterUI(myImage, this.layout);
            ((Activity) ctx).runOnUiThread(imgSetRunnable);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}