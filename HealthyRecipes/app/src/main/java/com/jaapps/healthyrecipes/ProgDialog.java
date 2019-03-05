package com.jaapps.healthyrecipes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by jithin on 30/12/18.
 */

public class ProgDialog extends AppCompatActivity
{
    ImageView imageV;
    ProgressBar progressBar;
    int position=0;
    View view;
    private AlertDialog.Builder builder;
    private Dialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.progdialog);
        boolean keep = getIntent().getExtras().getBoolean("keep");
        if(keep) {
            init();
           /* changeImageSlider();*/
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        super.onBackPressed();
    }
    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        boolean keep = intent.getExtras().getBoolean("keep");
        if(keep==false)
        {
            ProgDialog.this.finish();
        }
    }

/*    public ProgDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.progdialog, null);
        init();
    }*/

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.progAnimImage);
      //  imageV = ((ImageView) findViewById(R.id.progAnimImage));
        //builder = new AlertDialog.Builder(view.getContext());
    }
    public void show() {
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        /*changeImageSlider();*/
    }

    public void dismiss() {
        dialog.dismiss();
    }

   /* private void changeImageSlider() {
        position++;
        switch (position)
        {
            case 1:{ imageV.setImageResource(R.drawable.p1); }break;
            case 2:{ imageV.setImageResource(R.drawable.p2); }break;
            case 3:{ imageV.setImageResource(R.drawable.p3); }break;
            case 4:{ imageV.setImageResource(R.drawable.p4); }break;
            case 5:{ imageV.setImageResource(R.drawable.p5); }break;
            case 6:{ imageV.setImageResource(R.drawable.p6); }break;
            case 7:{ imageV.setImageResource(R.drawable.p7); }break;
            case 8:{ imageV.setImageResource(R.drawable.p8); }break;
            default:{position=0;}
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeImageSlider();
            }
        }, 100);
    }*/
}
