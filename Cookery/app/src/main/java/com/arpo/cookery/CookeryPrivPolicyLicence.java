package com.arpo.cookery;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class CookeryPrivPolicyLicence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookery_priv_policy_licence);

        int type = getIntent().getIntExtra("type", -1);

        Typeface typeface = Typeface.createFromAsset(getAssets(),
                String.format(Locale.US, "fonts/%s", "font.ttf"));


        TextView txtHeading = findViewById(R.id.licPolHeading);
        TextView txtContent = findViewById(R.id.licPolContent);

        txtContent.setTypeface(typeface);
        txtHeading.setTypeface(typeface);

        if( type == 1)
        {
            txtHeading.setText("Licence");
        }
        else if ( type == 2)
        {
            txtHeading.setText("Privacy policy");
        }
    }
}
