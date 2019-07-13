package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity {

    private Button product;
    private Button price;
    private Button sale;
    private FireStore firedb;
    private List<Price> prices;


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
        // when i=-1, loop will display heading of each column
        // then usually data will be display from i=0 to jArray.length()
        for (int i = -1; i < prices.size(); i++) {
            TableRow tr = new TableRow(LoginActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (flag == 1) {
                TextView b3 = new TextView(LoginActivity.this);
                b3.setText("Date");
                b3.setTextColor(Color.BLUE);
                b3.setTextSize(15);
                tr.addView(b3);
                TextView b4 = new TextView(LoginActivity.this);
                b4.setPadding(10, 0, 0, 0);
                b4.setTextSize(15);
                b4.setText("content");
                b4.setTextColor(Color.BLUE);
                tr.addView(b4);
                TextView b5 = new TextView(LoginActivity.this);
                b5.setPadding(10, 0, 0, 0);
                b5.setText("product");
                b5.setTextColor(Color.BLUE);
                b5.setTextSize(15);
                tr.addView(b5);
                tv.addView(tr);
                final View vline = new View(LoginActivity.this);
                vline.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                vline.setBackgroundColor(Color.BLUE);
                tv.addView(vline); // add line below heading
                flag = 0;
            } else {
                Price p = prices.get(i);

                TextView b = new TextView(LoginActivity.this);
                String str = String.valueOf(p.getDescription());
                b.setText(str);
                b.setTextColor(Color.RED);
                b.setTextSize(12);
                tr.addView(b);
                TextView b1 = new TextView(LoginActivity.this);
                b1.setPadding(10, 0, 0, 0);
                b1.setTextSize(12);
                String str1 = p.getStart_date() ;
                b1.setText(str1);
                b1.setTextColor(Color.RED);
                tr.addView(b1);
                TextView b2 = new TextView(LoginActivity.this);
                b2.setPadding(10, 0, 0, 0);
                String str2 = p.getEnd_date();
                b2.setText(str2);
                b2.setTextColor(Color.RED);
                b2.setTextSize(12);
                tr.addView(b2);
                tv.addView(tr);
                final View vline1 = new View(LoginActivity.this);
                vline1.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                vline1.setBackgroundColor(Color.WHITE);
                tv.addView(vline1);  // add line below each row
            }
        }
    }

}
