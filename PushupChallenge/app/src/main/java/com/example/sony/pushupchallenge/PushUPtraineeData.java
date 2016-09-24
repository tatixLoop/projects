package com.example.sony.pushupchallenge;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by jithin suresh on 24-09-2016.
 */
public class PushUPtraineeData {

    private int stamina;
    private Context mContext;

    boolean isTakenStaminaTest()
    {
        Databasepushup db;
        db = new Databasepushup(mContext);
        db.openConnection();

        String se = "select * from tb_staminatest";
        Cursor c = db.selectData(se);


        if(c.getCount() > 0) {
            db.closeConnection();
            return true;
        }
        else {
            db.closeConnection();
            return false;
        }

    }

    int getStamina()
    {
        Databasepushup db;
        db = new Databasepushup(mContext);
        db.openConnection();

        String se = "select * from tb_staminatest";
        Cursor c = db.selectData(se);
        while(c.moveToNext())
        {
            Log.d("JKS","no= "+c.getString(0)+")pushUps = "+c.getString(1)+"  stamina= "+c.getString(2) +"milliseconds per pushup");
            stamina = c.getInt(2);
        }
        db.closeConnection();
        return stamina;
    }
    void loadData()
    {
        Databasepushup db;
        db = new Databasepushup(mContext);
        db.openConnection();

        String se = "select * from tb_staminatest";
        Cursor c = db.selectData(se);

        if(c.getCount() > 0) {
            while(c.moveToNext())
            {
                Log.d("JKS","no= "+c.getString(0)+")pushUps = "+c.getString(1)+"  stamina= "+c.getString(2) +"milliseconds per pushup");
                stamina = c.getInt(2);
            }
        }

    }

    PushUPtraineeData(Context context)
    {
        mContext = context;
        loadData();

    }
}
