package com.arpo.cookery;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

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

    static List<ListItemDishes> FullDishList = new ArrayList<>();

    static Context gContext;
    static CookeryData sqlData;


    static  String dishName[] = {
            "unknown recipe",
            /* type1 */ "Breakfast recipe's",
            /* type2 */ "Lunch recipe's",
            /* type3 */ "Snacks recipe's",
            /* type4 */ "Juice recipe's",
            /* type5 */ "Cookie recipe's",
            /* type6 */ "Todays Special",
            /* type7 */ "Omlet Recipes",
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
        Globals.sqlData.addItemToShopList(igrd, dish, id);
    }

    static void removeIngredientFromShopList(Context ctx, String ingredient, int id)
    {
        Globals.sqlData.removeItemFromShopList(ingredient, id);
    }

    static int getIngredientShopList(Context ctx, List<ShoppingListItem> list)
    {
        Globals.sqlData.getShopListData(list);
        return list.size();
    }
    static void print(String str)
    {
        Log.d("JKS",str);
    }

    static boolean isFavorite(int id)
    {
        return Globals.sqlData.isFavorite(id);
    }

    static void clearFavorite(int id)
    {
        Globals.sqlData.clearFavorite(id);

        for (ListItemDishes dish: FullDishList
                ) {
            if (dish.getId() == id)
            {
                dish.setFav(false);
                break;
            }
        }
    }

    static void setFavorite(int id)
    {
        Globals.sqlData.setFavorite(id);
        for (ListItemDishes dish: FullDishList
             ) {
            if (dish.getId() == id)
            {
                dish.setFav(true);
                break;
            }
        }
    }
}
