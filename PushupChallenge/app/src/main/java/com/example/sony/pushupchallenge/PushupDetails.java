package com.example.sony.pushupchallenge;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;

public class PushupDetails extends FragmentActivity {
    Button bt_challenge;
    TextView tv_des;

    int[] imgs1;
    String fid="";
    //={R.mipmap.popo, R.mipmap.download, R.mipmap.pic, R.mipmap.pushup};
    ViewPager mViewPager;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    Databasepushup db;
    String des="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup_details);
        bt_challenge= (Button) findViewById(R.id.btn_challenge);
        tv_des= (TextView) findViewById(R.id.tv_des);
        db = new Databasepushup(this);
        db.openConnection();

         fid = getIntent().getStringExtra("fid");

        getImages();
        getDescription();
        bt_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create object of Trainee data;
                PushUPtraineeData trainee = new PushUPtraineeData(getApplicationContext());
                if(trainee.isTakenStaminaTest() == true)
                {
                    Intent i=new Intent(PushupDetails.this,Counter_display.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i=new Intent(PushupDetails.this,StaminaTestActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);




    }

    private  void getImages() {
        int i=0;
        String sel = "select image from tb_pushupimages where Pushup_id='" + fid + "'";
        Cursor c = db.selectData(sel);

        if (c != null) {
            int r=c.getCount();//to find no fo rows in cursor
            imgs1=new int[r];
            while (c.moveToNext()) {
                imgs1[i]=c.getInt(0);
                i++;

            }


        }

    }


    private void getDescription()
    {

        String se="select Steps from tb_description where Pushup_id= '"+fid+"'";
        Cursor c=db.selectData(se);
        int i=1;
        if (c != null) {
            while(c.moveToNext()) {
                String s1 = c.getString(0);
                des=des+"Step "+i+":\n";//+s1+"\n";
                String[] s2= s1.split(".:");
                for(int j=0;j<s2.length;j++){
                    des=des+s2[j]+".\n";
                }
                i++;
            }
            tv_des.setText(des);

        }

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
            return imgs1.length;
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
