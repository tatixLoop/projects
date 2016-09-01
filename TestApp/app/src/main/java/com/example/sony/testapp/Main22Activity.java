package com.example.sony.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main22Activity extends AppCompatActivity implements View.OnClickListener {


//INTENT PROGRAM
 Button btn_save;
    EditText et_number1;
    EditText et_number2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        et_number1= (EditText) findViewById(R.id.et_num1);
        et_number2= (EditText) findViewById(R.id.et_num2);
        btn_save= (Button) findViewById(R.id.btn_go);

        btn_save.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        String a=et_number1.getText().toString();
        String b=et_number2.getText().toString();
        int a1 =Integer.parseInt(a);
        int b1=Integer.parseInt(b);


    }
}
