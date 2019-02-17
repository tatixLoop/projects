package com.jaapps.cookegg;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CookeryShopList extends AppCompatActivity {

    List<ShoppingListItem> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookery_shop_list);

        shoppingList = new ArrayList<>();

        if (Globals.getIngredientShopList(getApplicationContext(), shoppingList) == 0)
        {
            Toast.makeText(this, "Shopping list is empty !!!", Toast.LENGTH_SHORT).show();
            finish();
        }


        ActionBar bar = getSupportActionBar();
        bar.setTitle("Shopping List");

        ListView lv = findViewById(R.id.lv_shopList);
        AdapterShoppingList shpAdapter = new AdapterShoppingList(this, shoppingList);
        lv.setAdapter(shpAdapter);
    }



    void print(String str)
    {
        Log.d("JKS", str);
    }


    class AdapterShoppingList extends BaseAdapter {
        List<ShoppingListItem> list;
        Context context;

        AdapterShoppingList(Context ctx, List<ShoppingListItem> l) {
            list = l;
            context = ctx;
        }

        @Override
        public int getCount() {
            return list.size(); //returns total of items in the list
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
        public View getView(int position, View convertView, ViewGroup parent) {


                final ShoppingListItem data = list.get(position);
                final int pos = position;

                TextView dataText;

                if (data.type == 0)
                {
                    convertView = LayoutInflater.from(context).
                            inflate(R.layout.shop_list_title, parent, false);

                    dataText = convertView.findViewById(R.id.txt_shopListTitle);

                    dataText.setText("For "+data.data);
                }
                else
                {
                    convertView = LayoutInflater.from(context).
                            inflate(R.layout.shop_list_ingredient, parent, false);
                    dataText = convertView.findViewById(R.id.txt_shopListIngredient);

                    dataText.setText(data.data);

                    RelativeLayout btn_remove = convertView.findViewById(R.id.btn_removeIngredient);
                    btn_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            shoppingList.remove(pos);
                            notifyDataSetChanged();
                            Globals.removeIngredientFromShopList(context, data.data, data.id);
                        }
                    });
                }

            return convertView;
        }


    }
}
