package com.arpo.cookery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

/**
 * Created by jithin on 10/9/18.
 */

public class Globals {


    static String host="http://tatixtech.com/";
    static String appdir="/cookery/";
    static String apipath="/api/";
    static String img_path="/img/";

/*    static String host="http://192.168.43.207/";
    static String appdir="/app/news/cookery/";
    static String apipath="/api/";
    static String img_path="/img/";*/


    static int FETCHTYPE_DISHCATAGORY = 1;
    static int FETCHTYPE_DISH = 2;
    static int FETCHTYPE_DISH_TITLE = 3;

    static List<ListItemDishes> FullDishList;


    static  String dishName[] = {
            "unknown recipe",
            /* type1 */ "Breakfast recipe's",
            /* type2 */ "Lunch recipe's",
            /* type3 */ "Snacks recipe's",
            /* type4 */ "Juice recipe's",
            /* type5 */ "Cookie recipe's",
            /* type6 */ "Todays Special",
            /* type7 */ "Your Favorite Omlet Recipes",
            /* type8 */ "Egg Dishes",
            /* type9 */ "Chicken Recipes",
            /* type10 */ "Coffee Recipes",
            /* type11 */ "Deserts",
            /* type12 */ "Healthy",
            /* type13 */ "IceCreams",
            /* type14 */ "Pizza",
            /* type15 */ "Sea Food Delicacy",
            /* type16 */ "Indian",
            /* type17 */ "Chinese",
            /* type18 */ "American",
            /* type19 */ "Salads",
    };

    static void addIngredientToShopList(Context ctx, String igrd, String dish, int id)
    {

        // get shlist_id_count
        {
            String shPrefKeyDishCount = "SHLIST_COUNT";
            String shPrefKeyDishData = "SHLIST_";
            String shPrefKeyIngrCount = "SHLIST_" + id + "_COUNT";
            String shPrefKeyIngrdata = "SHLIST_" + id ;

            SharedPreferences preferences_ingrCount = ctx.getSharedPreferences(shPrefKeyIngrCount, Context.MODE_PRIVATE);
            SharedPreferences preferences_ingrData = ctx.getSharedPreferences(shPrefKeyIngrdata, Context.MODE_PRIVATE);
            SharedPreferences preferences_dishCount = ctx.getSharedPreferences(shPrefKeyDishCount, Context.MODE_PRIVATE);

            SharedPreferences.Editor editShlistIngrData = preferences_ingrData.edit();
            SharedPreferences.Editor editShListIngrCount = preferences_ingrCount.edit();
            SharedPreferences.Editor editShlistDishCount = preferences_dishCount.edit();

            int ingrCount = preferences_ingrCount.getInt(shPrefKeyIngrCount, 0);
            int dishCount = preferences_dishCount.getInt(shPrefKeyDishCount, 0);

            SharedPreferences preferences_dishData = ctx.getSharedPreferences(shPrefKeyDishData + dishCount, Context.MODE_PRIVATE);
            SharedPreferences.Editor editShlistDishData = preferences_dishData.edit();

            print("Ingredient count ("+shPrefKeyIngrCount+") = "+ingrCount);
            print("DishCount ("+shPrefKeyDishCount+") = "+dishCount);
            if ( ingrCount == 0)
            {
                // add dish name in entry 0
                editShlistIngrData.putString(shPrefKeyIngrdata + "_"+ingrCount, dish);
                editShlistIngrData.apply();

                //Update shList
                editShlistDishData.putInt(shPrefKeyDishData + dishCount, id);
                editShlistDishData.apply();

                print("Add new key "+shPrefKeyDishData + dishCount +" as "+id);

                dishCount++;
                editShlistDishCount.putInt(shPrefKeyDishCount, dishCount);
                editShlistDishCount.apply();

                print("Add new key "+shPrefKeyIngrdata + "_"+ingrCount + " as "+ dish);
                print ("Add new key "+shPrefKeyDishCount +" as "+ dishCount);


                ingrCount++;
            }
            // add ingredient name
            editShlistIngrData.putString(shPrefKeyIngrdata + "_"+ingrCount, igrd);
            editShlistIngrData.apply();
            ingrCount++;


            print("Add new key "+shPrefKeyIngrdata + "_"+ingrCount + " as "+igrd);
            print("Add new key "+shPrefKeyIngrCount + " as "+ingrCount);

            // update ingredient count
            editShListIngrCount.putInt(shPrefKeyIngrCount, ingrCount);
            editShListIngrCount.apply();


            editShlistIngrData.apply();
            editShListIngrCount.apply();
            editShlistDishData.apply();
            editShlistDishCount.apply();

            Log.d("JKS","Shopping list count "+ingrCount);

        }


    }

    static void removeIngredientFromShopList(Context ctx, String ingredient, int id)
    {

        String shPrefKeyIngrdata = "SHLIST_" + id ;
        String shPrefKeyIngrCount = "SHLIST_" + id + "_COUNT";
        String shPrefKeyDishCount = "SHLIST_COUNT";
        SharedPreferences preferences_ingrData = ctx.getSharedPreferences(shPrefKeyIngrdata, Context.MODE_PRIVATE);
        SharedPreferences preferences_ingrCount = ctx.getSharedPreferences(shPrefKeyIngrCount, Context.MODE_PRIVATE);

        SharedPreferences.Editor editShListIngrCount = preferences_ingrCount.edit();
        SharedPreferences.Editor editShlistIngrData = preferences_ingrData.edit();

        print("<<<Remove>>>");

        //Get count of ingredients
        int ingrCount = preferences_ingrCount.getInt(shPrefKeyIngrCount, 0);
        if( ingrCount  > 0) {

            int j = 0;
            while (j < ingrCount) {

                String name = preferences_ingrData.getString(shPrefKeyIngrdata + "_" + j, "invalid");
                print("Value (" + shPrefKeyIngrdata + "+" + j + ")= " + name);
                if (name.equals(ingredient)) {
                    // add ingredient name
                    editShlistIngrData.putString(shPrefKeyIngrdata + "_" + j, "invalid");
                    editShlistIngrData.apply();
                    break;
                }
                j++;
            }
            ingrCount--;
            print("INGR count = "+ingrCount);

            // update ingredient count
            editShListIngrCount.putInt(shPrefKeyIngrCount, ingrCount);
            editShListIngrCount.apply();


            if (ingrCount == 1) {

                // update ingredient count
                editShListIngrCount.putInt(shPrefKeyIngrCount, 0);
                editShListIngrCount.apply();

                // add ingredient name
                editShlistIngrData.putString(shPrefKeyIngrdata + "_" + 0, "invalid");
                editShlistIngrData.apply();

                SharedPreferences preferences_dishCount = ctx.getSharedPreferences(shPrefKeyDishCount, Context.MODE_PRIVATE);
                int dishCount = preferences_dishCount.getInt(shPrefKeyDishCount, 0);

                for(int i =  0 ; i < dishCount; i++) {

                    String shPrefKeyDishData = "SHLIST_"+i;
                    SharedPreferences preferences_dishData = ctx.getSharedPreferences(shPrefKeyDishData + dishCount, Context.MODE_PRIVATE);
                    if (preferences_dishData.getInt(shPrefKeyDishData + i, 0) == id )
                    {
                        SharedPreferences.Editor editShlistDishData = preferences_dishData.edit();
                        //Update shList
                        editShlistDishData.putInt(shPrefKeyDishData + i, -1);
                        editShlistDishData.apply();
                        break;
                    }
                }
                SharedPreferences.Editor editShlistDishCount = preferences_dishCount.edit();

                dishCount--;
                editShlistDishCount.putInt(shPrefKeyDishCount, dishCount);
                editShlistDishCount.apply();

            }
        }
    }

    static int getIngredientShopList(Context ctx, List<ShoppingListItem> list)
    {
        String shPrefKeyDishCount = "SHLIST_COUNT";
        String shPrefKeyDishData = "SHLIST_";

        SharedPreferences preferences_dishCount = ctx.getSharedPreferences(shPrefKeyDishCount, Context.MODE_PRIVATE);
        int dishCount = preferences_dishCount.getInt(shPrefKeyDishCount, 0);

        print("DishCount ("+shPrefKeyDishCount+") = "+dishCount);

        int ret = dishCount;


        int i = 0;
        while ( i < dishCount)
        {
            SharedPreferences preferences_dishData = ctx.getSharedPreferences(shPrefKeyDishData + i, Context.MODE_PRIVATE);
            // GET Ids
            int id = preferences_dishData.getInt(shPrefKeyDishData + i, 0);

            String shPrefKeyIngrdata = "SHLIST_" + id ;
            String shPrefKeyIngrCount = "SHLIST_" + id + "_COUNT";
            SharedPreferences preferences_ingrData = ctx.getSharedPreferences(shPrefKeyIngrdata, Context.MODE_PRIVATE);
            SharedPreferences preferences_ingrCount = ctx.getSharedPreferences(shPrefKeyIngrCount, Context.MODE_PRIVATE);


            print("id ("+shPrefKeyDishData + i +")="+id);

            if ( id == -1)
            {
                i++;
                dishCount++;
                continue;
            }

            //Get count of ingredients
            int ingrCount = preferences_ingrCount.getInt(shPrefKeyIngrCount, 0);

            print("Ingredient count ("+shPrefKeyIngrCount+") = "+ingrCount);

            int j = 0;
            while (j < ingrCount)
            {

                String name = preferences_ingrData.getString(shPrefKeyIngrdata+"_"+j, "invalid");
                print("Value ("+shPrefKeyIngrdata+"+"+j+")= "+name);
                if ( name.equals("invalid"))
                {
                    ingrCount++;
                    j++;
                    continue;
                }
                else
                {
                    ShoppingListItem data = new ShoppingListItem();
                    data.data = name;
                    data.id = id;
                    if ( j == 0) {
                        data.type = 0;
                    }
                    else
                    {
                        data.type = 1;
                    }
                    j++;
                    list.add(data);
                }
            }
            i++;
        }
        return ret;
    }
    static void print(String str)
    {
        Log.d("JKS",str);
    }
}
