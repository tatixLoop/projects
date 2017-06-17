package com.ttx.photopuzzle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PhotoPuzzle_Selected extends AppCompatActivity {

    TextView tv_puzzleselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_puzzle__selected);


        tv_puzzleselected= (TextView) findViewById(R.id.tv_name);

        int type = getIntent().getIntExtra("puzzleselected",0);

        if(type == 2)
            tv_puzzleselected.setText("2x2");

        if(type == 3)
            tv_puzzleselected.setText("3x3");

        if(type == 4)
            tv_puzzleselected.setText("4x4");

        if(type == 5)
            tv_puzzleselected.setText("5x5");

        Log.d("PS","type = "+ tv_puzzleselected.getText().toString());
       /* tv_puzzleselected.setText(message);*/

    }

}
