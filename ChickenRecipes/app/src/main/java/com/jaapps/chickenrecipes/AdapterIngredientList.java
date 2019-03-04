package com.jaapps.chickenrecipes;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by jithin on 11/9/18.
 */

public class AdapterIngredientList extends BaseAdapter {
    List<ListItemIngredients> list;
    Context context;
   LinearLayout layout;  // for snack bar

    AdapterIngredientList(Context ctx, List<ListItemIngredients> l) {
        list = l;
        context = ctx;
    }

    void print(String str)
    {
        //Log.d("JKS",str);
    }

    //  snack bar
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.show();
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size(); //returns total of items in the list
        }
        else
        {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return list.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_ingredientlist, parent, false);

            final ListItemIngredients item = list.get(position);

            layout=convertView.findViewById(R.id.linearIngredient); // for snack bar

            TextView txt_ingredient = convertView.findViewById(R.id.txt_ingredient);
            txt_ingredient.setText(item.getIngredient());

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    String.format(Locale.US, "fonts/%s", "font.ttf"));

           /* ((TextView) convertView.findViewById(R.id.txt_qty)).setTypeface(typeface);*/
            txt_ingredient.setTypeface(typeface);

            final RelativeLayout checkBox = convertView.findViewById(R.id.chkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(item.selected == 0) {
                        item.selected = 1;
                        checkBox.setBackground(context.getResources().getDrawable(R.drawable.tickcarty));
                        setSnackBar(layout,item.getIngredient() +" added To Your Shopping List");
                        Globals.addIngredientToShopList(context, item.getIngredient(), item.getDishName(), item.getId());
                    }
                    else
                    {
                        checkBox.setBackground(context.getResources().getDrawable(R.drawable.carty));
                        item.selected = 0;
                        setSnackBar(layout,item.getIngredient()+" removed From Your Shopping List");
                        Globals.removeIngredientFromShopList(context, item.getIngredient(),  item.getId());
                    }

                }
            });

        }
        return convertView;
    }
}
