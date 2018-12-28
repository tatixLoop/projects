package com.arpo.cookery;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
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
    Context ctx;
    boolean stretching;
    RelativeLayout support;

    public DishImageSetterUI(Bitmap bmp, RelativeLayout rel, RelativeLayout support, Context ctx, boolean strech) {
        this.bitmap = bmp;
        this.layout = rel;
        imgSet = false;
        this.ctx = ctx;
        this.stretching = strech;
        this.support = support;
    }

    public DishImageSetterUI(Bitmap bmp, ImageView img, Context ctx, boolean strech) {
        this.bitmap = bmp;
        this.img = img;
        imgSet = true;
        this.ctx = ctx;
        this.stretching = strech;
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


                if (this.stretching ) {
                    int width = this.bitmap.getWidth();
                    int height = this.bitmap.getHeight();

                    Resources r = ctx.getResources();
                    float px = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            170,
                            r.getDisplayMetrics()
                    );
                    float newHeight = px * height / width;
                    
                    layout.getLayoutParams().height = (int) newHeight;

                    if( support != null)
                    {
                        support.setPadding(0, (int)newHeight, 0, 0);
                    }
                }

                this.layout.setBackgroundDrawable(dr);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}