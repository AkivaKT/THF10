package com.byui.thf10;

import android.annotation.SuppressLint;
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

    private FireStore firedb;
    private List<Sale> sales;
    private BroadcastReceiver tableRenew;


    /**
     * This is the intial page that users are lead to after they log-in.
     * They are presented three buttons to navigate the app. The decision
     * Is whether or not they should add or delete a new sale, product or price.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // main button
        Button product = findViewById(R.id.product);
        Button price   = findViewById(R.id.price);
        Button sale    = findViewById(R.id.sale);

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

        // update table when data was sent
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

    /**
     * Button for Product, Price and Sales pages.
     */
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

    /**
     * Sales data is pulled initially so that it can populate the table.
     * As sales are the values with the most input, the table will display all the recent
     * sales that have been made across the company.
     */
    public void pullData(){
        sales = new ArrayList<>();
        firedb.pullCollection("Sales", "com.byui.thf10.Sale", new CallBackList() {
            @Override
            public void onCallback(List<Object> jsonList) {
                for (Object i : jsonList) {
                    sales.add((Sale) i);
                    Log.d(TAG, "table data loaded.");
                }
                Toast.makeText(getApplicationContext(), "check the table", Toast.LENGTH_SHORT).show();
                makeTable();
            }
        });
    }

    /**
     * Said table to display the sales.
     */
    @SuppressLint("SetTextI18n")
    private void makeTable() {
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
        int row = 1;
        for (int i = -1; i < sales.size(); i++) {
            TableRow tr = new TableRow(LoginActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (row == 1) {
                // create text view and headings
                TextView c1 = new TextView(LoginActivity.this);
                c1.setText("Date");
                c1.setTextColor(Color.BLUE);
                c1.setTextSize(15);
                tr.addView(c1);
                TextView c2 = new TextView(LoginActivity.this);
                c2.setPadding(10, 0, 0, 0);
                c2.setTextSize(15);
                c2.setText("Product");
                c2.setTextColor(Color.BLUE);
                tr.addView(c2);
                TextView c3 = new TextView(LoginActivity.this);
                c3.setPadding(15, 0, 0, 0);
                c3.setText("Quantity");
                c3.setTextColor(Color.BLUE);
                c3.setTextSize(15);
                tr.addView(c3);
                TextView c4 = new TextView(LoginActivity.this);
                c4.setPadding(15, 0, 0, 0);
                c4.setText("Amount");
                c4.setTextColor(Color.BLUE);
                c4.setTextSize(15);
                tr.addView(c4);
                TextView c5 = new TextView(LoginActivity.this);
                c5.setPadding(15, 0, 0, 0);
                c5.setText("Sale");
                c5.setTextColor(Color.BLUE);
                c5.setTextSize(15);
                tr.addView(c5);
                tv.addView(tr);
                final View view = new View(LoginActivity.this);
                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.BLUE);
                tv.addView(view); // add line below heading
                row = 0;
            } else {
                Sale sale = sales.get(i);
                TextView v1 = new TextView(LoginActivity.this);
                String str = String.valueOf(sale.getDate());
                v1.setText(str);
                v1.setTextColor(Color.RED);
                v1.setTextSize(15);
                tr.addView(v1);
                TextView v2 = new TextView(LoginActivity.this);
                v2.setPadding(10, 0, 0, 0);
                v2.setTextSize(15);
                String str1 = sale.getProduct().getName();
                v2.setText(str1);
                v2.setTextColor(Color.RED);
                tr.addView(v2);
                TextView v3 = new TextView(LoginActivity.this);
                v3.setPadding(15, 0, 0, 0);
                String str2 = String.valueOf(sale.getQuantity());
                v3.setText(str2);
                v3.setTextColor(Color.RED);
                v3.setTextSize(15);
                tr.addView(v3);
                TextView v4 = new TextView(LoginActivity.this);
                v4.setPadding(15, 0, 0, 0);
                String str4 = "$" + (sale.getPrice().getPrice() * Integer.parseInt(sale.getQuantity()));
                v4.setText(str4);
                v4.setTextColor(Color.RED);
                v4.setTextSize(15);
                tr.addView(v4);
                TextView v5 = new TextView(LoginActivity.this);
                v5.setPadding(15, 0, 0, 0);
                String str5 = sale.getAccount();
                v5.setText(str5);
                v5.setTextColor(Color.RED);
                v5.setTextSize(15);
                tr.addView(v5);
                tv.addView(tr);
                final View view = new View(LoginActivity.this);
                view.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                view.setBackgroundColor(Color.BLACK);
                tv.addView(view);  // add line below each row
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(tableRenew);
    }
}

