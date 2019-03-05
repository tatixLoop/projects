package com.jaapps.healthyrecipes;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.List;
import java.util.Locale;

import static com.jaapps.healthyrecipes.Globals.print;

public class AdapterCatagoryList extends BaseAdapter {
    List<ListSubCatagory> list;
    Context context;

    AdapterCatagoryList(Context ctx, List<ListSubCatagory> l) {
        list = l;
        context = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListSubCatagory item = list.get(position);
        float dpWidth = context.getResources().getDisplayMetrics().widthPixels / context.getResources().getDisplayMetrics().density;


        if( item.getConvertView() == null) {
            if (item.getType() == 0 && item.getConvertView() == null) {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.subcatagory_item, parent, false);


                TextView txt_title = convertView.findViewById(R.id.txt_title);
                txt_title.setText(item.getText());
                Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                        String.format(Locale.US, "fonts/%s", "fontfront.ttf"));
                txt_title.setTypeface(typeface);


                RelativeLayout rel_view = convertView.findViewById(R.id.rel_view);
                //rel_dishbox.setBackgroundColor(Color.BLACK);


                DishImageFetcher imgFetch1 = new DishImageFetcher(Globals.FETCHTYPE_DISHSUBCATAGORY, item.getSubcatagory(), item.getBgUrl(), rel_view, context, true);
                imgFetch1.setWidth((int) dpWidth);
                imgFetch1.setLayoutFront(txt_title);
                new Thread(imgFetch1).start();

                item.setConvertView(convertView);
            } else {
                convertView = LayoutInflater.from(context).
                        inflate(R.layout.subcatagory_ad, parent, false);

                AdView mAdView;
                RelativeLayout relAdView = convertView.findViewById(R.id.rel_adview);
                mAdView = new AdView(context);
                relAdView.addView(mAdView);
                mAdView.setAdUnitId(context.getResources().getString(R.string.banner_home_menu1));

                AdSize customSize = new AdSize((int)dpWidth, 200);
                mAdView.setAdSize(customSize);

                AdRequest adRequestBanner = new AdRequest.Builder()
                        .addTestDevice("C9DCF6327A4B5E68DCC320AC2E54036C")
                        .build();
                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                    }

                    @Override
                    public void onAdClosed() {
                        print("cAd is closed!");
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        print( "cAd failed to load! error code: " + errorCode);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        print( "cAd left application!");
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }
                });


                mAdView.loadAd(adRequestBanner);
                item.setConvertView(convertView);
            }
        }
        else
        {
            convertView = item.getConvertView();
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position); //returns list item at the specified position
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size(); //returns total of items in the list
        }
        else
        {
            return 0;
        }
    }
}
