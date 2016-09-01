package com.example.sony.pushup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {


    List<String> names;
    List<Integer> img;
    ListView lv_homelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        img=new ArrayList<>();
        img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);
     img.add(R.drawable.logo);










        names=new ArrayList<>();
        names.add("Standard Push");
        names.add("Knee Push");
        names.add("Shoulder Tap");
        names.add("Hand Tap");
        names.add("T Push");
        names.add("Tight Tap");
        names.add("Single hand Raised");
        names.add("Single Leg Raised");
        names.add("Knuckle Pushup");
        names.add("Staggered Pushup");
        names.add("Alligator Pushup");
        names.add("Slow To Knee");
        names.add("Spider Man Pushup");
        names.add("Knee To Chest");
        names.add("Pseudo Plainchee Pushup");
        names.add("Outside Leg Kick");
        names.add("Grass Hopper Pushup");
        names.add("Foot Tap");
        names.add("Knee To Opposite");
        names.add("Cross Screw Pushup Eblow");

        names.add("Diamond Pushup");
        names.add("Wide Pushup");
        names.add("Tiger Pushup");
        names.add("Pike Pushup");
        names.add("Feet Elivated Pushup");
        names.add("Side Roll Pushup");
        names.add("Jack Knife Pushup");
        names.add("Yoga Pushup");

        names.add("Explosive Staggered Pushup");
        names.add("Explosive Jacks Pushup");
        names.add("Clap Pushup");
        names.add("Slider Pushup");
        names.add("Feet On Wall Pushup");
        names.add("Super Man Pushup");
        names.add("Finger Tip Pushup");






        lv_homelist = (ListView) findViewById(R.id.lv_home);

        CustomAdapter customListAdapter=new CustomAdapter(Home.this, names, img);
        lv_homelist.setAdapter(customListAdapter);
        lv_homelist.setOnItemClickListener(this);


    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new android.os.Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



       // Toast.makeText(Home.this, "name=" + names.get(position), Toast.LENGTH_SHORT).show();
        if (names.get(position).equals("Standard Push"))
        {
            Intent i= new Intent(Home.this,StandardPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Knee Push"))
        {
            Intent i= new Intent(Home.this,Kneepushup.class);
            startActivity(i);

            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Shoulder Tap"))
        {
            Intent i= new Intent(Home.this,ShoulderTap.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
      else  if (names.get(position).equals("Hand Tap"))
        {
            Intent i= new Intent(Home.this,Handatap.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("T Push"))
        {
            Intent i= new Intent(Home.this,Tpushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Tight Tap"))
        {
            Intent i= new Intent(Home.this,TightTap.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Single hand Raised"))
        {
            Intent i= new Intent(Home.this,SingleHandRaised.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Single Leg Raised"))
        {
            Intent i= new Intent(Home.this,SingleLegRaised.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Knuckle Pushup"))
        {
            Intent i= new Intent(Home.this,KnucklePushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name is selected" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Staggered Pushup"))
        {
            Intent i= new Intent(Home.this,StaggeredPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name " + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Alligator Pushup"))
        {
            Intent i= new Intent(Home.this,AlligatorPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name " + names.get(position), Toast.LENGTH_SHORT).show();

        }
      else  if (names.get(position).equals("Slow To Knee"))
        {
            Intent i= new Intent(Home.this,SlowToKnee.class);
            startActivity(i);
            Toast.makeText(Home.this, "name " + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Spider Man Pushup"))
        {
            Intent i= new Intent(Home.this,SpiderManPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name " + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Knee To Chest"))
        {
            Intent i= new Intent(Home.this,KneeToChest.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Pseudo Plainchee Pushup"))
        {
            Intent i= new Intent(Home.this,PseudoPlaincheePushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Outside Leg Kick"))
        {
            Intent i= new Intent(Home.this,OutsideLegKick.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Grass Hopper Pushup"))
        {
            Intent i= new Intent(Home.this,GrassHopperPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Foot Tap"))
        {
            Intent i= new Intent(Home.this,FootTap.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Knee To Opposite"))
        {
            Intent i= new Intent(Home.this,KneeToOpposite.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Cross Screw Pushup Eblow"))
        {
            Intent i= new Intent(Home.this,CrossScrewPushupEblow.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Diamond Pushup"))
        {
            Intent i= new Intent(Home.this,DiamondPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Wide Pushup"))
        {
            Intent i= new Intent(Home.this,WidePushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Tiger Pushup"))
        {
            Intent i= new Intent(Home.this,TigerPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
      else  if (names.get(position).equals("Pike Pushup"))
        {
            Intent i= new Intent(Home.this,PikePushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Feet Elivated Pushup"))
        {
            Intent i= new Intent(Home.this,FeetElivatedPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Side Roll Pushup"))
        {
            Intent i= new Intent(Home.this,SideRollPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Jack Knife Pushup"))
        {
            Intent i= new Intent(Home.this,JackKnifePushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Yoga Pushup"))
        {
            Intent i= new Intent(Home.this,YogaPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Explosive Staggered Pushup"))
        {
            Intent i= new Intent(Home.this,ExplosiveStaggeredPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Explosive Jacks Pushup"))
        {
            Intent i= new Intent(Home.this,ExplosiveJackPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
      else  if (names.get(position).equals("Clap Pushup"))
        {
            Intent i= new Intent(Home.this,ClapPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Slider Pushup"))
        {
            Intent i= new Intent(Home.this,SliderPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Feet On Wall Pushup"))
        {
            Intent i= new Intent(Home.this,FeetOnWallPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
       else if (names.get(position).equals("Super Man Pushup"))
        {
            Intent i= new Intent(Home.this,SuperManPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }
        else if (names.get(position).equals("Finger Tip Pushup"))
        {
            Intent i= new Intent(Home.this,FingerTipPushup.class);
            startActivity(i);
            Toast.makeText(Home.this, "name" + names.get(position), Toast.LENGTH_SHORT).show();

        }






    }
}
