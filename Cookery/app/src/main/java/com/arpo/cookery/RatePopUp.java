package com.arpo.cookery;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RatePopUp extends AppCompatActivity {

    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;

    int rateVal;

    int rating;
    int numRating;
    int gId;
    int newRating;
    JSONParser jParser = new JSONParser();

    private ProgressDialog pDialog;

    void print(String str) {
        Log.d("JKS", str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_pop_up);

        rating = getIntent().getIntExtra("rating", -1);
        numRating = getIntent().getIntExtra("numRating", -1);
        gId = getIntent().getIntExtra("id", -1);

        print(" ID :  " + gId + " Ratings : " + rating + "  NumRating = " + numRating);

        star1 = findViewById(R.id.rateStar1);
        star2 = findViewById(R.id.rateStar2);
        star3 = findViewById(R.id.rateStar3);
        star4 = findViewById(R.id.rateStar4);
        star5 = findViewById(R.id.rateStar5);

        rateVal = 0;


        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateVal = 2;
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rateVal = 4;
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateVal = 6;
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateVal = 8;
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateVal = 10;
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.star));

            }
        });

        Button rate = findViewById(R.id.btn_rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateVal == 0) {
                    Toast.makeText(getApplicationContext(), "Please Select Star", Toast.LENGTH_SHORT).show();
                } else {
                    newRating = ((rating * numRating) + rateVal) / (numRating + 1);
                    print("New rating = " + newRating);
                    //finish();
                    numRating++;

                    new updateRating().execute();

                }
            }
        });

    }

    class updateRating extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = ProgressDialog.show(
                    RatePopUp.this,
                    "Please wait...",
                    "Thank you for submitting your rating. Please wait ",
                    false,
                    false,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {

                        }
                    }
            );
        }

        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("id", gId + ""));
            params.add(new BasicNameValuePair("rating", newRating + ""));
            params.add(new BasicNameValuePair("numRating", numRating + ""));

            String apiname = "";
            apiname = "updateRating.php";

            JSONObject json = jParser.makeHttpRequest(Globals.host + Globals.appdir + Globals.apipath + apiname,
                    "GET", params);
            if (json != null) {
                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else
            {
                print("Error in making http request");
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all dishlist
            pDialog.dismiss();
            finish();
        }

    }
}
