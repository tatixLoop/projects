package com.appsecurity.tatix.menzworld;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.math.BigInteger;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class AddItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_item);

        final CheckBox chk_shirt = (CheckBox) findViewById(R.id.chk_shirt);
        final CheckBox chk_jeans = (CheckBox)findViewById(R.id.chk_jeans);
        final CheckBox chk_others = (CheckBox)findViewById(R.id.chk_others);
        final CheckBox chk_tshirt = (CheckBox) findViewById(R.id.chk_tshirt);
        final CheckBox chk_pants = (CheckBox)findViewById(R.id.chk_pants);
        final CheckBox chk_shorts = (CheckBox)findViewById(R.id.chk_shorts);
        final CheckBox chk_inner = (CheckBox) findViewById(R.id.chk_inner);
        final CheckBox chk_belt = (CheckBox)findViewById(R.id.chk_belt);
        final CheckBox chk_wallet = (CheckBox)findViewById(R.id.chk_wallets);

        final CheckBox chk_s = (CheckBox) findViewById(R.id.ckh_sx_s);
        final CheckBox chk_m = (CheckBox) findViewById(R.id.ckh_sx_m);
        final CheckBox chk_l = (CheckBox) findViewById(R.id.ckh_sx_l);
        final CheckBox chk_xl = (CheckBox) findViewById(R.id.ckh_sx_xl);
        final CheckBox chk_xxl = (CheckBox) findViewById(R.id.ckh_sx_sxxl);
        final CheckBox chk_other_sz = (CheckBox) findViewById(R.id.ckh_sx_othr);
        final TextView txt_sz_other = (TextView) findViewById(R.id.txt_dz_other);
        txt_sz_other.setVisibility(View.GONE);



        final EditText txt_price = (EditText)findViewById(R.id.txt_price);
        final EditText txt_selprice = (EditText)findViewById(R.id.txt_selprice);
        final EditText txt_numItems = (EditText)findViewById(R.id.txt_numItems);
        final EditText txt_brand = (EditText)findViewById(R.id.txt_brand);



        Button scan = (Button) findViewById(R.id.btn_barcode);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentIntegrator scanIntegrator = new IntentIntegrator(AddItemActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        Button save = (Button)findViewById(R.id.btn_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = -1;
                int price = 0;
                int sellingPrice = 0;
                int soldPrice = 0;
                int noOfItems = 1;
                String brand ="";
                int size = 0;

                brand = txt_brand.getText().toString();
                if(chk_s.isChecked())
                    size = MainActivity.SZ_SMALL;
                else if(chk_m.isChecked())
                    size = MainActivity.SZ_MEDIUM;
                else if(chk_l.isChecked())
                    size = MainActivity.SZ_LARGE;
                else if(chk_xl.isChecked())
                    size = MainActivity.SZ_XLARGE;
                else if(chk_xxl.isChecked())
                    size = MainActivity.SZ_XXLARGE;
                else if (chk_other_sz.isChecked())
                {
                    size = Integer.parseInt(txt_sz_other.getText().toString());
                }
                else size = -1;

                if(chk_jeans.isChecked())
                    item = MainActivity.JEANS;
                else if(chk_shirt.isChecked())
                    item = MainActivity.SHIRT;
                else if(chk_shorts.isChecked())
                    item = MainActivity.SHORTS;
                else if(chk_pants.isChecked())
                    item = MainActivity.PANTS;
                else if(chk_tshirt.isChecked())
                    item = MainActivity.TSHIRTS;
                else if(chk_inner.isChecked())
                    item = MainActivity.INNERWAER;
                else if(chk_belt.isChecked())
                    item = MainActivity.BELTS;
                else if(chk_wallet.isChecked())
                    item = MainActivity.WALLET;
                else if(chk_others.isChecked())
                    item = MainActivity.OTHERS;

                MainActivity.serialNumber ++;


                if(item == -1 || txt_price.getText().toString().matches("") ||
                        txt_selprice.getText().toString().matches("") ||
                        txt_numItems.getText().toString().matches(""))
                {

                    Toast.makeText(getBaseContext(), "Please enter details of the item",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                price = Integer.parseInt( txt_price.getText().toString() );
                sellingPrice = Integer.parseInt( txt_selprice.getText().toString() );
                noOfItems = Integer.parseInt(txt_numItems.getText().toString());
                TextView barcode = (TextView)findViewById(R.id.txt_barcode);


                BigInteger barcode_int = new BigInteger(barcode.getText().toString(),10);

                String querry =
                        "INSERT INTO stockData (serialNumber,barcodeId, item, price, selling_price, noOfItems,itemsSold,stockDate,brand,size) values ("
                                                +MainActivity.serialNumber +","+barcode_int+","+item +","+price+","+sellingPrice+","+noOfItems+",0"+",'"+getDateTime()+"','"+brand+"'"+","+size +")";


                MainActivity.mdb.execSQL(querry);

                Intent intent = new Intent(AddItemActivity.this, ShowSerial.class);
                intent.putExtra("serial",MainActivity.serialNumber);
                startActivity(intent);
                finish();


            }
        });

        chk_jeans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_others.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_shirt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_tshirt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);

                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_pants.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);

                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_shorts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);

                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_inner.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);

                    chk_belt.setChecked(false);
                    chk_wallet.setChecked(false);
                }

            }
        });
        chk_belt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);

                    chk_wallet.setChecked(false);
                }

            }
        });

        chk_wallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_jeans.setChecked(false);
                    chk_shirt.setChecked(false);
                    chk_others.setChecked(false);
                    chk_tshirt.setChecked(false);
                    chk_pants.setChecked(false);
                    chk_shorts.setChecked(false);
                    chk_inner.setChecked(false);
                    chk_belt.setChecked(false);

                }

            }

        });

        chk_s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_m.setChecked(false);
                    chk_l.setChecked(false);
                    chk_xl.setChecked(false);
                    chk_xxl.setChecked(false);
                    chk_other_sz.setChecked(false);
                    txt_sz_other.setVisibility(View.GONE);

                }

            }
        });
        chk_m.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_s.setChecked(false);
                    chk_l.setChecked(false);
                    chk_xl.setChecked(false);
                    chk_xxl.setChecked(false);
                    chk_other_sz.setChecked(false);
                    txt_sz_other.setVisibility(View.GONE);

                }

            }
        });
        chk_l.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_m.setChecked(false);
                    chk_s.setChecked(false);
                    chk_xl.setChecked(false);
                    chk_xxl.setChecked(false);
                    chk_other_sz.setChecked(false);
                    txt_sz_other.setVisibility(View.GONE);

                }

            }
        });
        chk_xl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_m.setChecked(false);
                    chk_l.setChecked(false);
                    chk_s.setChecked(false);
                    chk_xxl.setChecked(false);
                    chk_other_sz.setChecked(false);
                    txt_sz_other.setVisibility(View.GONE);

                }

            }
        });
        chk_xxl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_m.setChecked(false);
                    chk_l.setChecked(false);
                    chk_xl.setChecked(false);
                    chk_s.setChecked(false);
                    chk_other_sz.setChecked(false);
                    txt_sz_other.setVisibility(View.GONE);

                }

            }
        });
        chk_other_sz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // perform logic
                    chk_m.setChecked(false);
                    chk_l.setChecked(false);
                    chk_xl.setChecked(false);
                    chk_xxl.setChecked(false);
                    chk_s.setChecked(false);
                    txt_sz_other.setVisibility(View.VISIBLE);

                }

            }
        });

    }
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            Log.d("JKS","scan content! " + scanContent);
            Log.d("JKS","scan fmt ! " + scanFormat);
            TextView barcode = (TextView)findViewById(R.id.txt_barcode);
            barcode.setText(scanContent);
            if(barcode.getText().toString().matches(""))
            {
                Log.d("JKS","Nothing scaned");
                barcode.setText("000000000000");
            }

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
            TextView barcode = (TextView)findViewById(R.id.txt_barcode);
            barcode.setText("00000000000");
        }
    }
}
