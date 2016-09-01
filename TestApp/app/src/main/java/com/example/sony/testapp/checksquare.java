package com.example.sony.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class checksquare extends AppCompatActivity implements View.OnClickListener {


    EditText ed_txt;
    Button btn_pro;
    TextView txt_sq;
    TextView txt_cub;
    TextView txt_pri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checksquare);
        ed_txt= (EditText) findViewById(R.id.edt_txt);
        btn_pro= (Button) findViewById(R.id.btn_process);
        txt_sq= (TextView) findViewById(R.id.tv_checksquare_squareview);
        txt_cub= (TextView) findViewById(R.id.txt_cube);
        txt_pri= (TextView) findViewById(R.id.txt_prime);

        btn_pro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String a = ed_txt.getText().toString();
       //String b = ed_txt.getText().toString();
        int v1 = Integer.parseInt(a);
       // int w = Integer.parseInt(b);

        if (btn_pro == v) {
            int x = (v1 * v1);
            txt_sq.setText("Square=" + x);
            int n = (v1 * v1 * v1);
            txt_cub.setText("Cube=" + n);
            Intent i=new Intent(checksquare.this,Main23Activity.class);
            i.putExtra("k_sqr",x);
            startActivity(i);


        }
    }
    }