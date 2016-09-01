package com.example.sony.testapp;

import android.net.Uri;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class Add extends AppCompatActivity implements View.OnClickListener {
    TextView txt_view;
    Button btn_result;
    EditText edt_txt1;
    EditText edt_txt2;
    Button btn_substration;
    Button btn_multiplication;
    Button btn_divition;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edt_txt1 = (EditText) findViewById(R.id.edt_no1);
        edt_txt2 = (EditText) findViewById(R.id.edt_no2);
        btn_result = (Button) findViewById(R.id.btn_add);
        txt_view = (TextView) findViewById(R.id.txtv_result);
        btn_substration = (Button) findViewById(R.id.btn_sub);
        btn_multiplication= (Button) findViewById(R.id.btn_mul);
        btn_divition= (Button) findViewById(R.id.btn_div);


        btn_result.setOnClickListener(this);
        btn_substration.setOnClickListener(this);
        btn_multiplication.setOnClickListener(this);
        btn_divition.setOnClickListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {

        String a = edt_txt1.getText().toString();
        String b = edt_txt2.getText().toString();
        int a1 = Integer.parseInt(a);
        int b1 = Integer.parseInt(b);

        edt_txt1.setText("");
        edt_txt2.setText("");





        if (btn_result == v)

        {
            int c = a1 + b1;
            txt_view.setText(c + "");

        }
        else if (btn_substration==v)
        {

            int c = a1 - b1;
            txt_view.setText(c + "");


        }
        else if (btn_multiplication==v)
        {

            int c = a1 * b1;
            txt_view.setText(c + "");

    }

        else if (btn_divition==v)
        {
            int c = a1 / b1;
            txt_view.setText(c + "");

        }
        }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Add Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sony.testapp/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Add Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sony.testapp/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
