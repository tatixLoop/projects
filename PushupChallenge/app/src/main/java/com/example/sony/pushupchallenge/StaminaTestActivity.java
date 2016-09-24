package com.example.sony.pushupchallenge;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StaminaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamina_test);

        Button testButtonStaminaTest = (Button) findViewById(R.id.btn_statinaTest);
        testButtonStaminaTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Databasepushup db;
                db = new Databasepushup(getApplicationContext());
                db.openConnection();

                String se = "select * from tb_staminatest";
                Cursor c = db.selectData(se);

                if(c.getCount() <= 0)
                {
                    db.insertData("insert into tb_staminatest (Date,pushupno,Timetaken,Score) values('test_date', 50, 10, 10)");
                }
            }
        });
    }
}
