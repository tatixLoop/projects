package com.jaapps.saladrecipes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    int width;
    ImageView imgListPageGrad;
    CollapsingToolbarLayout colLayout;
    boolean listPageImgSet;

    TextView layoutFrnt;
    boolean layoutFrontSet;

    public DishImageSetterUI(Bitmap bmp, RelativeLayout rel, RelativeLayout support, Context ctx, boolean strech, int width) {
        this.bitmap = bmp;
        this.layout = rel;
        imgSet = false;
        this.ctx = ctx;
        this.stretching = strech;
        this.support = support;
        this.width = width;
        listPageImgSet = false;
        this.layoutFrontSet = false;
    }

    public DishImageSetterUI(Bitmap bmp, ImageView img, Context ctx, boolean strech, int width) {
        this.bitmap = bmp;
        this.img = img;
        imgSet = true;
        this.ctx = ctx;
        this.stretching = strech;
        this.width = width;
        listPageImgSet = false;
        this.layoutFrontSet = false;
    }

    public void setListPageImage(ImageView view, CollapsingToolbarLayout colLayout)
    {
        listPageImgSet = true;
        this.imgListPageGrad = view;
        this.colLayout = colLayout;
    }

    public void setLayoutFront(TextView layout)
    {
        this.layoutFrnt = layout;
        this.layoutFrontSet = true;
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
                            this.width,
                            r.getDisplayMetrics()
                    );

                    float newHeight = px * height / width;

                    layout.getLayoutParams().height = (int) newHeight;

                    if( support != null)
                    {
                       // support.setPadding(0, (int)newHeight, 0, 0);
                    }

                    if(listPageImgSet)
                    {
                        this.imgListPageGrad.getLayoutParams().height = (int)newHeight;
                        this.colLayout.getLayoutParams().height = (int)newHeight;
                    }
                    if(layoutFrontSet)
                    {
                        this.layoutFrnt.getLayoutParams().height = (int)newHeight;
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
