package com.arpo.cookery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by jithin on 10/9/18.
 */

public class DishImageSetterUI implements Runnable {
    RelativeLayout layout;
    Bitmap bitmap;
    ImageView img;
    boolean imgSet;

    public DishImageSetterUI(Bitmap bmp, RelativeLayout rel) {
        this.bitmap = bmp;
        this.layout = rel;
        imgSet = false;
    }

    public DishImageSetterUI(Bitmap bmp, ImageView img) {
        this.bitmap = bmp;
        this.img = img;
        imgSet = true;
    }

    public void run()
    {
        try {
            Drawable dr = new BitmapDrawable(this.bitmap);
            if (imgSet)
            {
                this.img.setImageDrawable(dr);
            }
            else {
                this.layout.setBackgroundDrawable(dr);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}