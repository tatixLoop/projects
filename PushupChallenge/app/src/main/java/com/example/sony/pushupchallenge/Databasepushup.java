package com.example.sony.pushupchallenge;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by SONY on 06-09-2016.
 */
public class Databasepushup extends SQLiteOpenHelper {

    SQLiteDatabase sqldb;
    Context c;






    public Databasepushup(Context C)
    {
        super(C,"DB_pushup",null,1);
        c=C;
    }
    public void openConnection()
    {
        sqldb=getWritableDatabase();
    }

    public void closeConnection()
    {
        sqldb.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {



        String qury = "create table tb_staminatest(Testno INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, numPushUp INTEGER NOT NULL ,Timetaken INTEGER NOT NULL )";
        db.execSQL(qury);

        String qury1 = "create table tb_userdata(Userid INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(30) NOT NULL , height INTEGER NOT NULL ,weight INTEGER NOT NULL ,Score INTEGER NOT NULL)";
        db.execSQL(qury1);


        String qury2 = "create table tb_pushupdata(Data_id INTEGER PRIMARY KEY AUTOINCREMENT ,Pushup_id INTEGER NOT NULL , Data VARCHAR(500) NOT NULL ,Attemptno VARCHAR(30) NOT NULL ,Numpushup INTEGER NOT NULL,Timetaken INTEGER NOT NULL,Score INTEGER NOT NULL,Badge INTEGER NOT NULL )";
        db.execSQL(qury2);


        String qury3 = "create table tb_pushupdetails(Pushup_id INTEGER PRIMARY KEY AUTOINCREMENT , excersisename VARCHAR(500),images VARCHAR(100)  )";
        db.execSQL(qury3);


        String qury4 = "create table tb_description( Description_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,Pushup_id INTEGER , Steps VARCHAR(100) )";
        db.execSQL(qury4);


        String qury5 = "create table tb_pushupimages(Image_id INTEGER PRIMARY KEY AUTOINCREMENT ,Pushup_id INTEGER  , image VARCHAR(30) )";
        db.execSQL(qury5);

       // Toast.makeText(c, "created", Toast.LENGTH_SHORT).show();




// push up name and image insetion
        int[]img ={R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic,R.mipmap.download,R.mipmap.pushup,R.mipmap.popo,R.mipmap.pic};

        String[] names ={"STANDARD","KNEE PUSHUP","SHOULDER TAP","HAND TAP","T PUSHUP","TIGHT TAP","SINGLE HAND RAISED"," SINGLE LEG RAISED","KNUCKLE PUSHUP","STAGGERED PUSHUP","ALLIGATOR PUSHUP","SLOW TO KNEE","SPIDERMAN PUSHUP","KNEE TO CHEST","PSEUDO PLAINCHEE PUSHUP","OUTSIDE LEG KICK","GRASSHOPPER PUSHUP","FOOT TAP","KNEE TO OPPOSITE ","CROSS SCREW PUSHUP ELBOW","DIAMOND PUSHUP","WIDE PUSHUP","TIGER PUSHUP","PIKE PUSHUP","FEET ELIVATED PUSHUP","SIDE ROLL PUSHUP","JACK KNIFE PUSHUP","YOGA PUSHUP","EXPLOSIVE STAGGERED PUSHUP","EXPLOSIVE JACKS PUSHUP","CLAP PUSHUP","SLIDER PUSHUP","FEET ON WALL","SUPERMAN PUSHUP","FINGER TIP PUSHUP"};

        for(int i=0;i<names.length;i++) {
            String st1 = "insert into tb_pushupdetails(excersisename,images )values('"+names[i]+"','"+img[i]+"')";
            db.execSQL(st1);
        }

// description inserion .... only first five excersise  detais is correct

        String[] s1={"Place your hands firmly on the ground, directly under shoulders.: Ground your toes into the floor.: Tighten your abs and flatten your back so your entire body is neutral and straight.:","Keep your back flat.:\n" +
                "Inhale as you begin to lower your body until your chest touches the floor.: \n" +
                "Your body should remain in a straight line from head to toe.: \n","Keep your core engaged.:\n" +
                "Exhale as you push back to the starting position.: \n"};

        String[] s2={"Lower yourself till nearly touching the ground.\n" +
                " Push yourself up till back to starting position and tap your left hand back with Right hand.then bring it back to starting position.\n"," Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and tap your right hand back with Left hand, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s3={" Lower yourself till nearly touching the ground.\n" +
                " Push yourself up till back to starting position and tap your Right Shoulder with left Hand.then bring it back to starting position.\n"," Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and tapLeft Shoulder with right hand, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s4={"Lower yourself till nearly touching the ground.\n" +
                " Push yourself up till back to starting position and Raise your left hand.then bring it back to starting position.\n","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Hand, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s5={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s6={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s7={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s8={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s9={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s10={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s11={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s12={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s13={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s14={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s15={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s16={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s17={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s18={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s19={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s21={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s20={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s22={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s23={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s24={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s25={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s26={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s27={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s28={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s29={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s30={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s31={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s32={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s33={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"},s34={"Lower yourself till nearly touching the ground Push yourself up till back to starting position and Raise your left Leg.then bring it back to starting position.","Lower yourself till nearly touching the ground again.\n" +
                " Push yourself up till back to starting position and Raise your Right Leg, then bring it back to starting position. \n" +
                " Inhale when going up, exhale when going down.\n"};


        for(int i=0;i<s1.length;i++){
            String des1="insert into tb_description(Pushup_id,steps)values('1','"+s1[i]+"')";
            db.execSQL(des1);
        }
        int[] img1={R.mipmap.download, R.mipmap.pic, R.mipmap.popo,R.mipmap.pushup};

        for(int i=0;i<img1.length;i++){
            String des2="insert into tb_pushupimages(Pushup_id,image)values('1','"+img1[i]+"')";
            db.execSQL(des2);
        }


        for(int i=0;i<s2.length;i++){
            String des3="insert into tb_description(Pushup_id,steps)values('2','"+s2[i]+"')";
            db.execSQL(des3);
        }
        int[] img2={R.mipmap.download, R.mipmap.pic, R.mipmap.popo,R.mipmap.pushup};

        for(int i=0;i<img2.length;i++){
            String des4="insert into tb_pushupimages(Pushup_id,image)values('2','"+img2[i]+"')";
            db.execSQL(des4);
        }
        for(int i=0;i<s3.length;i++){
            String des5="insert into tb_description(Pushup_id,steps)values('3','"+s3[i]+"')";
            db.execSQL(des5);
        }
        int[] img3={R.mipmap.download, R.mipmap.pic, R.mipmap.popo,R.mipmap.pushup};

        for(int i=0;i<img3.length;i++){
            String des6="insert into tb_pushupimages(Pushup_id,image)values('3','"+img3[i]+"')";
            db.execSQL(des6);
        }
        for(int i=0;i<s4.length;i++){
            String des7="insert into tb_description(Pushup_id,steps)values('4','"+s4[i]+"')";
            db.execSQL(des7);
        }
        int[] img4={R.mipmap.download, R.mipmap.pic, R.mipmap.popo,R.mipmap.pushup};

        for(int i=0;i<img4.length;i++){
            String des8="insert into tb_pushupimages(Pushup_id,image)values('4','"+img4[i]+"')";
            db.execSQL(des8);
        }

       // Toast.makeText(c, "inserted", Toast.LENGTH_SHORT).show();
    }

    public boolean insertData(String qury)
    {
        try {
            sqldb.execSQL(qury);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public Cursor selectData(String qury)
    {
        Cursor cr=sqldb.rawQuery(qury,null);
        return cr;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
