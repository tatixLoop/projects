package com.example.sony.pushupchallenge;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PushupDetails extends FragmentActivity implements View.OnClickListener{
Button bt_challenge;

    int[] imgs1={R.mipmap.popo, R.mipmap.download, R.mipmap.pic, R.mipmap.pushup};
    ViewPager mViewPager;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_details);
bt_challenge= (Button) findViewById(R.id.btn_challenge);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);




    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent(PushupDetails.this,Counter_display.class);
        startActivity(i);
    }


    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, imgs1[i]);
            //args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }


    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_collection_object, container, false);
            Bundle args = getArguments();
            ((ImageView)rootView.findViewById(R.id.img_fragment)).setImageResource(args.getInt(ARG_OBJECT));
//            ((TextView) rootView.findViewById(R.id.txt1)).setText(
//                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }




}
