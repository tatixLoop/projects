package com.appsecurity.tatix.menzworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowSerial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_serial);
        TextView txtSerial  = (TextView)findViewById(R.id.txt_serialNumber);

        Intent intent = getIntent();
        int serialNum = intent.getIntExtra("serial",0);
        txtSerial.setText(""+serialNum);

        Button btn_ok  = (Button)findViewById(R.id.btn_okSerial);
        btn_ok.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

    }
}
