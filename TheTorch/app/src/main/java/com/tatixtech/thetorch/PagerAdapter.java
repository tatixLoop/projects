package com.tatixtech.thetorch;

/**
 * Created by jithin on 4/3/18.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment_torch tab1 = new Fragment_torch();
                return tab1;
            case 1:
                Fragment_screen tab2 = new Fragment_screen();
                return tab2;
            case 2:
                Fragment_disco tab3 = new Fragment_disco();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
