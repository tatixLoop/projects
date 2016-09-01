package com.example.sony.testapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv_message;
    Button bt_click_me;
    EditText et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_message= (TextView) findViewById(R.id.tv_msg);
        bt_click_me= (Button) findViewById(R.id.bt_click);
        et_msg= (EditText) findViewById(R.id.et_enter_msg);

        bt_click_me.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v==bt_click_me){
            String msg=et_msg.getText().toString();
            et_msg.setText("");
            tv_message.setText(msg);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
