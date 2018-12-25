package com.arpo.cookery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    int fetchType;
    int fetchId;

    public DishImageFetcher(int fetchType, int fetchId, String url, RelativeLayout rel, Context context)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.position = -1;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = false;
    }
    public DishImageFetcher(int fetchType, int fetchId, String url, RelativeLayout rel, Context context, List<ListItemDishes> list, int position)
    {
        this.url = url;
        this.layout = rel;
        this.ctx = context;
        this.dishList = list;
        this.position = position;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = false;
    }

    public DishImageFetcher(int fetchType, int fetchId, String url, ImageView img, Context context)
    {
        this.url = url;
        this.img = img;
        this.ctx = context;
        this.position = -1;
        this.fetchType = fetchType;
        this.fetchId = fetchId;
        this.setImgView = true;
    }


    public void run()
    {
        DishImageSetterUI imgSetRunnable;
        try {
            Bitmap myImage;
            // check if image is present in shared preference cache
            String sharedPrefKey = "SHCache_"+fetchType+"_"+fetchId;
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
            String value = preferences.getString(sharedPrefKey, "defaultValue");

            if( !value.equals("defaultValue"))
            {
                Log.d("JKS", "Decrypt shared preference to bitmap");
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
                Log.d("JKS","Setting preview image for "+this.dishList.get(this.position).getName());
            }

            if (this.setImgView ) {
                imgSetRunnable = new DishImageSetterUI(myImage, this.img);
                ((Activity) ctx).runOnUiThread(imgSetRunnable);
            }
            else {
                imgSetRunnable = new DishImageSetterUI(myImage, this.layout);
                ((Activity) ctx).runOnUiThread(imgSetRunnable);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}