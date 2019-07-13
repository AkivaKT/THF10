package com.byui.thf10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private Button product;
    private Button price;
    private Button sale;
    private FireStore firedb;
    private List<Price> prices;
    private BroadcastReceiver tableRenew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // main button
        product = findViewById(R.id.product);
        price   = findViewById(R.id.price);
        sale    = findViewById(R.id.sale);

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productActivity();

            }
        });
        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceActivity();

            }
        });
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleActivity();

            }
        });

        //database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);

        pullData();


        tableRenew = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                pullData();
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(tableRenew, new IntentFilter("Table needs renewal"));
    }

    public void productActivity(){
        Intent productIntent = new Intent(this, ProductActivity.class);
        startActivity(productIntent);
    }

    public void priceActivity(){
        Intent priceIntent = new Intent(this, PriceActivity.class);
        startActivity(priceIntent);
    }

    public void saleActivity(){

        Intent intent3 = new Intent(this, SalesActivity.class);
        startActivity(intent3);
    }

    public void pullData(){
        prices = new ArrayList<>();
        firedb.pullCollection("Prices", "com.byui.thf10.Price", new CallBackList() {
            @Override
            public void onCallback(List<Object> jsonList) {
                for (Object i : jsonList) {
                    prices.add((Price) i);
                    Log.d(TAG, "data loaded.");
                }
                Toast.makeText(getApplicationContext(), "check the table", Toast.LENGTH_SHORT).show();
                maketable();
            }
        });
    }

    private void maketable() {

        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
        int flag = 1;
        for (int i = -1; i < prices.size(); i++) {
            TableRow tr = new TableRow(LoginActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (flag == 1) {
                TextView c1 = new TextView(LoginActivity.this);
                c1.setText("Date");
                c1.setTextColor(Color.BLUE);
                c1.setTextSize(15);
                tr.addView(c1);
                TextView c2 = new TextView(LoginActivity.this);
                c2.setPadding(10, 0, 0, 0);
                c2.setTextSize(15);
                c2.setText("content");
                c2.setTextColor(Color.BLUE);
                tr.addView(c2);
                TextView c3 = new TextView(LoginActivity.this);
                c3.setPadding(10, 0, 0, 0);
                c3.setText("product");
                c3.setTextColor(Color.BLUE);
                c3.setTextSize(15);
                tr.addView(c3);
                tv.addView(tr);
                final View vline = new View(LoginActivity.this);
                vline.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tv.addView(vline); // add line below heading
                flag = 0;
            } else {
                Price p = prices.get(i);
                TextView v1 = new TextView(LoginActivity.this);
                String str = String.valueOf(p.getDescription());
                v1.setText(str);
                v1.setTextColor(Color.RED);
                v1.setTextSize(12);
                tr.addView(v1);
                TextView v2 = new TextView(LoginActivity.this);
                v2.setPadding(10, 0, 0, 0);
                v2.setTextSize(12);
                String str1 = p.getStart_date() ;
                v2.setText(str1);
                v2.setTextColor(Color.RED);
                tr.addView(v2);
                TextView v3 = new TextView(LoginActivity.this);
                v3.setPadding(10, 0, 0, 0);
                String str2 = p.getEnd_date();
                v3.setText(str2);
                v3.setTextColor(Color.RED);
                v3.setTextSize(12);
                tr.addView(v3);
                tv.addView(tr);
                final View vline1 = new View(LoginActivity.this);
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.WHITE);
                tv.addView(vline1);  // add line below each row
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(tableRenew);
    }
}

