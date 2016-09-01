package com.example.sony.testapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Dtabaseeg3 extends AppCompatActivity {
TextView tv_no1;
    TextView tv_no2;
    TextView tv_result;
    DBconnection obj1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtabaseeg3);

        tv_no1= (TextView) findViewById(R.id.tv_numre1);
        tv_no2= (TextView) findViewById(R.id.tv_numre2);
        tv_result= (TextView) findViewById(R.id.tv_RESULT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        obj1=new DBconnection(Dtabaseeg3.this);
        obj1.openConnection();
        String s="select * from tb_sum ";
        Cursor g =obj1.selectData(s);
        if (g!=null) {
            if (g.moveToNext()) {
                String a1 = g.getString(0);
                String b1 = g.getString(1);
                String c1 = g.getString(2);

                tv_no1.setText(a1 + "");
                tv_no2.setText(b1 + "");
                tv_result.setText(c1 + "");
            }
        }
    }
}
