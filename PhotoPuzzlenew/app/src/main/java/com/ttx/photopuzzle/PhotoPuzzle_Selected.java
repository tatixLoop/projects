package com.ttx.photopuzzle;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhotoPuzzle_Selected extends AppCompatActivity implements AdapterView.OnItemClickListener {

    TextView tv_puzzleselected;
    ListView lv;
    List<PhotoPuzzleListcls> list1;
    PhotoPuzzle_Data db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_puzzle__selected);
        lv = (ListView) findViewById(R.id.lv_list);

        tv_puzzleselected = (TextView) findViewById(R.id.tv_name1);

        int type = getIntent().getIntExtra("puzzleselected", 0);

        if (type == 2)
            tv_puzzleselected.setText("2x2");

        if (type == 3)
            tv_puzzleselected.setText("3x3");

        if (type == 4)
            tv_puzzleselected.setText("4x4");

        if (type == 5)
            tv_puzzleselected.setText("5x5");

        Log.d("PS", "type = " + tv_puzzleselected.getText().toString());


        list1 = new ArrayList<>();

        db = new PhotoPuzzle_Data(this);
        db.openConnection();

        getpuzzlelist(type);

        Adapter_PhotoPuzzlelist a = new Adapter_PhotoPuzzlelist(this, list1);
        lv.setAdapter(a);
        lv.setOnItemClickListener(this);


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        ////// go to page PhotoPuzzle_game

        int number = list1.get(i).getNo();

        Intent intent = new Intent(PhotoPuzzle_Selected.this, PhotoPuzzle_Game.class);

        intent.putExtra("fid", number);
        intent.putExtra("position",i);


        /// passs drwawable here

        String im= "R.Drawable."+"img_"+number+"_"+i+".jpg";
intent.putExtra("image",im);
        Log.d("PS",im);
        startActivity(intent);


    }


    private void getpuzzlelist(int ptypt) {


        String se = "select puzzleno,status,timetaken,puzzleimg from tb_photopuzzle where puzzletype="+ptypt;

        Cursor c = db.selectData(se);
        if (c != null) {
            while (c.moveToNext()) {


                int stat=c.getInt(1);
                int time=c.getInt(2);
                int n = c.getInt(0);
                int img = c.getInt(3);
                //int img = c.getInt(3);
                PhotoPuzzleListcls p1 = new PhotoPuzzleListcls();
                p1.setNo(n);
                p1.setStatus(stat);
                p1.setTimetaken(time);
                p1.setImage(img);

                list1.add(p1);

            }



        }


    }
}
