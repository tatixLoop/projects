package com.example.sony.pushupchallenge;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by jithin suresh on 24-09-2016.
 */
public class PushUPtraineeData {

    private boolean misTestTaken = false;

    boolean isTakenStaminaTest()
    {
        return misTestTaken;
    }


    PushUPtraineeData(Context context)
    {
        Databasepushup db;
        db = new Databasepushup(context);
        db.openConnection();

        String se = "select * from tb_staminatest";
        Cursor c = db.selectData(se);

        if(c.getCount() > 0)
            misTestTaken = true;
        else
            misTestTaken = false;


    }
}
