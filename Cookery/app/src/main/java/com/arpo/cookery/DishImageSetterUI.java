package com.arpo.cookery;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;

/**
 * Created by jithin on 10/9/18.
 */

public class DishImageSetterUI implements Runnable {
    RelativeLayout layout;
    Bitmap bitmap;

    public DishImageSetterUI(Bitmap bmp, RelativeLayout rel) {
        this.bitmap = bmp;
        this.layout = rel;
    }

    public void run()
    {
        try {
            Drawable dr = new BitmapDrawable(this.bitmap);
            this.layout.setBackgroundDrawable(dr);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}