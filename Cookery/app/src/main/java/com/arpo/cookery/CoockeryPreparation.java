package com.arpo.cookery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoockeryPreparation extends AppCompatActivity {

    void print(String str)
    {
        Log.d("JKS",str);
    }

    String gDish = "";
    JSONParser jParser = new JSONParser();
    JSONArray dishlist = null;
    JSONArray ingredientlist = null;
    JSONArray stepList = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DISH = "dishinfo";
    private static final String TAG_INGREDIENTS = "ingredients";
    private static final String TAG_STEPS = "steps";
    private static final String TAG_TYPE = "id";
    int gId;
    recipe cookRecipe;
    AdapterStepListView adapterStepList;
    AdapterIngredientList adapterIngredientList;

    TextView cookTime;
    TextView calory;
    TextView serveCount;
    TextView author;

    ListItemDishes data;

    GetDishInfo asyncFetch;

    boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coockery_preparation);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        data = (ListItemDishes)getIntent().getSerializableExtra("data");
        print("Id = "+data.getId() + " Name : "+data.getName());


        gDish = data.getName();
        gId = data.getId();

        isFav = isFavorite(data.getId());
        final FloatingActionButton faButton;
        faButton = findViewById(R.id.fab);

        if (!isFav)
        {
            faButton.setImageResource(R.drawable.favorites);
        }

        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFav)
                {
                    clearFavorite(gId);
                    faButton.setImageResource(R.drawable.favorites);
                    isFav = false;
                    Toast.makeText(getApplicationContext(),"Recipe removed from favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    setFavorite(gId);
                    faButton.setImageResource(R.drawable.favoritesred);
                    isFav = true;
                    Toast.makeText(getApplicationContext(),"Recipe added to favorites list", Toast.LENGTH_SHORT).show();
                }
            }
        });


        TextView title_text = findViewById(R.id.txt_recipeTitle);
        title_text.setText(""+data.getName());
        String title_image = Globals.host + Globals.appdir + Globals.img_path + "/" +
                data.getImg_path() + "/title_image.jpg";
        RelativeLayout title = findViewById(R.id.rel_title);

        DishImageFetcher imgFetch = new DishImageFetcher(Globals.FETCHTYPE_DISH_TITLE, gId, title_image, title, this, true);
        float dpWidth = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().density;
        imgFetch.setWidth((int)dpWidth);
        new Thread(imgFetch).start();

        cookRecipe = new recipe();

        cookTime = findViewById(R.id.txt_cooktime);
        serveCount = findViewById(R.id.txt_servings);
        author = findViewById(R.id.txt_author);
        calory = findViewById(R.id.txt_calories);


        calory.setText(data.getCalory() + " cal");
        serveCount.setText("serves "+data.getServeCount());
        author.setText(data.getAuthor());

        int seconds = data.getCooktimeinsec();
        int mins = seconds/60;

        cookTime.setText(mins + " Minutes");

        //Rating calcuation
        int rating = data.getRating();

        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                String.format(Locale.US, "fonts/%s", "font.ttf"));

        ((TextView) findViewById(R.id.txt_prep_prepsteps)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txt_prep_ingredients)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txt_share)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txt_prep_author_title)).setTypeface(typeface);
        ((TextView) findViewById(R.id.txt_prep_rateRecipeTitle)).setTypeface(typeface);


        title_text.setTypeface(typeface);
        cookTime.setTypeface(typeface);
        calory.setTypeface(typeface);
        serveCount.setTypeface(typeface);
        author.setTypeface(typeface);



        ImageView star1 = findViewById(R.id.img_star1);
        ImageView star2 = findViewById(R.id.img_star2);
        ImageView star3 = findViewById(R.id.img_star3);
        ImageView star4 = findViewById(R.id.img_star4);
        ImageView star5 = findViewById(R.id.img_star5);

        rating = rating/2;
        switch (rating)
        {
            case 1:
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                break;
            case 2:
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                break;
            case 3:
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                break;
            case 4:
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.unratingstar));
                break;
            case 5:
                star1.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star2.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star3.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star4.setImageDrawable(getResources().getDrawable(R.drawable.star));
                star5.setImageDrawable(getResources().getDrawable(R.drawable.star));
                break;

        }


        RelativeLayout share = findViewById(R.id.rel_rateRecipie);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print("Share is clicked");
                Intent popUp = new Intent(getApplicationContext(), RatePopUp.class);
                popUp.putExtra("rating",data.getRating());
                popUp.putExtra("numRating", data.getNumRating());
                popUp.putExtra("id", data.getId());
                startActivity(popUp);

            }
        });


        asyncFetch = new GetDishInfo();
        asyncFetch.execute();


    }
    //  snack bar
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }
    boolean isFavorite(int id)
    {
        String sharedPrefKey = "FAV_PREF"+id;
        SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
        int value = preferences.getInt(sharedPrefKey, -1);

        if( value == 1 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    void setFavorite(int id)
    {
        String sharedPrefKey = "FAV_PREF"+id;
        SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(sharedPrefKey, 1);
        editor.apply();
    }

    void clearFavorite(int id)
    {
        String sharedPrefKey = "FAV_PREF"+id;
        SharedPreferences preferences = getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(sharedPrefKey, -1);
        editor.apply();
    }

    class GetDishInfo extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent dial = new Intent(getApplicationContext(), ProgDialog.class);
            dial.putExtra("keep", true);
            startActivity(dial);
        }

        /**
         * getting All dishlist from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair(TAG_TYPE, gId+""));
            // getting JSON string from URL
            String apiname = "";
            apiname ="getDishInfo.php";

            JSONObject json = jParser.makeHttpRequest(Globals.host+Globals.appdir+Globals.apipath+apiname,
                    "GET", params);

            // Check your log cat for JSON reponse
            if(json != null) {
                //print( json.toString());
                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        /*dishlist = json.getJSONArray(TAG_DISH);

                        for (int i = 0; i < dishlist.length(); i++) {
                            JSONObject c = dishlist.getJSONObject(i);

                            cookRecipe.setAuthor(c.getString("author"));
                            cookRecipe.setId(c.getInt("id"));
                            cookRecipe.setCooktimeinsec(c.getInt("cooktimeinsec"));
                            cookRecipe.setCalory(c.getInt("calory"));
                            cookRecipe.setServeCount(c.getInt("serves"));
                            cookRecipe.setRating(c.getInt("rating"));

                            print("Id="+c.getInt("id")+
                                    " cooktime = "+c.getInt("cooktimeinsec")+
                                    " serves = "+c.getInt("serves")+
                                    " rating = "+c.getInt("rating")+
                                    " author = "+c.getString("author")+
                                    " calory="+c.getInt("calory")
                            );

                        }
*/
                        ingredientlist = json.getJSONArray(TAG_INGREDIENTS);
                        cookRecipe.setNumIngredients(ingredientlist.length());
                        String [] ingredients = new String[ingredientlist.length()];

                        List<ListItemIngredients> listIngredient = new ArrayList<>();
                        for (int i = 0; i < ingredientlist.length(); i++) {
                            JSONObject c = ingredientlist.getJSONObject(i);
                            ListItemIngredients ingredientItem = new ListItemIngredients(0,c.getString("ingredient"), data.getName(), data.getId());
                            listIngredient.add(ingredientItem);

                            ingredients[i] = c.getString("ingredient");
                            //print(c.getString("ingredient"));
                        }
                        cookRecipe.setIngredientList(listIngredient);
                        cookRecipe.setIngredientsp(ingredients);

                        stepList = json.getJSONArray(TAG_STEPS);
                        cookRecipe.setNumSteps(stepList.length());
                        String [] stepR = new String[stepList.length()];

                        List<ListItemSteps> listStepItem = new ArrayList<>();

                        for (int i = 0; i < stepList.length(); i++) {
                            JSONObject c = stepList.getJSONObject(i);
                            stepR[i] = c.getString("step");

                            ListItemSteps data = new ListItemSteps(c.getInt("stepno"),
                                    c.getString("step"));

                            listStepItem.add(data);

                            /*print("step no : "+c.getInt("stepno") +
                                    "step : "+c.getString("step")
                            );*/

                        }
                        cookRecipe.setStepList(listStepItem);
                        cookRecipe.setStep(stepR);

                    } else {
                        print("No dishes found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                print("Error in making http request");
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all dishlist
            //adapterDishList.notifyDataSetChanged();
            Intent dial = new Intent(getApplicationContext(), ProgDialog.class);
            dial.putExtra("keep", false);
            startActivity(dial);

            runOnUiThread(new Runnable() {
                public void run() {
/*
                    cookTime.setText(cookRecipe.getCooktimeinsec() + "sec");
                    calory.setText(cookRecipe.getCalory() + "cal");
                    serveCount.setText("serves "+cookRecipe.getServeCount());
                    author.setText(cookRecipe.getAuthor());
*/

                    ListView lv_steps = findViewById(R.id.lv_listSteps);
                    adapterStepList  = new AdapterStepListView(getApplicationContext(), cookRecipe.getStepList());
                    lv_steps.setAdapter(adapterStepList);
                    setListViewHeightBasedOnChildren(lv_steps);

                    ListView lv_ingredient = findViewById(R.id.lv_ingredient);
                    adapterIngredientList  = new AdapterIngredientList(getApplicationContext(), cookRecipe.getIngredientList());
                    lv_ingredient.setAdapter(adapterIngredientList);
                    setListViewHeightBasedOnChildren(lv_ingredient);

                    ScrollView preps = findViewById(R.id.scroll);
                    preps.scrollTo(0,0);
                    preps.fullScroll( View.FOCUS_UP);

                }
            });
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // class to store all cooking information
    class recipe {
        int numSteps;
        String step[];

        int numIngredients;
        String ingredientsp[];

        List<ListItemSteps > stepList;
        List<ListItemIngredients > ingredientList;

        public List<ListItemIngredients> getIngredientList() {
            return ingredientList;
        }

        public void setIngredientList(List<ListItemIngredients> ingredientList) {
            this.ingredientList = ingredientList;
        }

        int id;
        int cooktimeinsec;
        int serveCount;
        int calory;
        int rating;
        String author;

        public List<ListItemSteps> getStepList() {
            return stepList;
        }

        public void setStepList(List<ListItemSteps> stepList) {
            this.stepList = stepList;
        }

        public int getNumSteps() {
            return numSteps;
        }

        public void setNumSteps(int numSteps) {
            this.numSteps = numSteps;
        }

        public String[] getStep() {
            return step;
        }

        public void setStep(String[] step) {
            this.step = step;
        }

        public int getNumIngredients() {
            return numIngredients;
        }

        public void setNumIngredients(int numIngredients) {
            this.numIngredients = numIngredients;
        }

        public String[] getIngredientsp() {
            return ingredientsp;
        }

        public void setIngredientsp(String[] ingredientsp) {
            this.ingredientsp = ingredientsp;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCooktimeinsec() {
            return cooktimeinsec;
        }

        public void setCooktimeinsec(int cooktimeinsec) {
            this.cooktimeinsec = cooktimeinsec;
        }

        public int getServeCount() {
            return serveCount;
        }

        public void setServeCount(int serveCount) {
            this.serveCount = serveCount;
        }

        public int getCalory() {
            return calory;
        }

        public void setCalory(int calory) {
            this.calory = calory;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
