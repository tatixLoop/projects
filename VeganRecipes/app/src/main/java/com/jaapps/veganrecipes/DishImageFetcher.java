package com.jaapps.veganrecipes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
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
    ImageView img;
    boolean setImgView;
    boolean stretching;

    int fetchType;
    int fetchId;
    RelativeLayout support;

    int width;
    ImageView img_listPageLayoutGrad;
    CollapsingToolbarLayout colLayout;
    boolean listPageLayoutGradSet;

    TextView layoutFrnt;
    boolean layoutFrontSet;


    public DishImageFetcher(int fetchType, int fetchId, String url, RelativeLayout rel, Context context, boolean stretch)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.position = -1;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = false;
        this.stretching = stretch;
        support = null;
        width = 170;
        listPageLayoutGradSet = false;
        this.layoutFrontSet = false;
    }
    public DishImageFetcher(int fetchType, int fetchId, String url, RelativeLayout rel, RelativeLayout supportLayout, Context context, List<ListItemDishes> list, int position, boolean stretch)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.dishList = list;
        this.position = position;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = false;
        this.stretching = stretch;
        support = supportLayout;
        width = 170;
        listPageLayoutGradSet = false;
        this.layoutFrontSet = false;
    }

    public DishImageFetcher(int fetchType, int fetchId, String url, ImageView img, Context context, boolean stretch)
    {
        this.url = url;
        this.img = img;
        this.ctx = context;
        this.position = -1;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = true;
        this.stretching = stretch;
        support = null;
        width = 170;
        listPageLayoutGradSet = false;
        this.layoutFrontSet = false;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    public void setImgListPageLayoutGrad(ImageView img, CollapsingToolbarLayout colLayout)
    {
        listPageLayoutGradSet = true;
        this.img_listPageLayoutGrad = img;
        this.colLayout = colLayout;

    }

    public void setLayoutFront(TextView layout)
    {
        this.layoutFrnt = layout;
        this.layoutFrontSet = true;
    }

    public void run()
    {
        DishImageSetterUI imgSetRunnable;
        try {
            Bitmap myImage;
            // check if image is present in shared preference cache
            String sharedPrefKey = "SHCache_"+fetchType+"_"+fetchId;
            SharedPreferences preferences = ctx.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
            String value = preferences.getString(sharedPrefKey, "defaultValue");

            if( !value.equals("defaultValue"))
            {
                //Log.d("JKS", "Decrypt shared preference to bitmap");
                byte[] imageAsBytes = Base64.decode(value.getBytes(), Base64.DEFAULT);
                myImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            }
            else {
                URL url = new URL(this.url);
                myImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                if(fetchType != Globals.FETCHTYPE_DISH_TITLE && fetchType != Globals.FETCHTYPE_DISH) {
                    // save the image as string in shared preferences
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    myImage.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    String encoded = Base64.encodeToString(b, Base64.DEFAULT);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(sharedPrefKey, encoded);
                    editor.apply();
                }
            }
            if(this.position != -1) {

                Drawable dr = new BitmapDrawable(myImage);
                this.dishList.get(this.position).setPreviewImg(myImage);
                this.dishList.get(this.position).setPreviewSet(1);
           }

            if (this.setImgView ) {
                imgSetRunnable = new DishImageSetterUI(myImage, this.img, ctx, stretching, this.width);
                ((Activity) ctx).runOnUiThread(imgSetRunnable);
            }
            else {
                imgSetRunnable = new DishImageSetterUI(myImage, this.layout, this.support, ctx, stretching,this.width);
                if(listPageLayoutGradSet) {
                    imgSetRunnable.setListPageImage(this.img_listPageLayoutGrad, this.colLayout);
                }
                if (layoutFrontSet)
                {
                    imgSetRunnable.setLayoutFront(this.layoutFrnt);
                }
                ((Activity) ctx).runOnUiThread(imgSetRunnable);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
