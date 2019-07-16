package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.R.layout.simple_spinner_item;
import static android.content.ContentValues.TAG;

public class SalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner PriceSpinner;
    private Spinner ProductSpinner;
    private Spinner AccountSpinner;
    private EditText Quantity;
    private FireStore firedb;
    private ArrayList<Sale> salesList = new ArrayList<>();
    private List<Product> ProductList;
    private List<Price> PriceList;
    private List<String> ProductType;
    private List<String> PriceType;
    private Product product;
    private Price price;
    private String account;

    public SalesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);
        ProductList = new ArrayList<>();
        PriceList = new ArrayList<>();
        ProductType = new ArrayList<String>();
        PriceType = new ArrayList<String>();

        pullProducts();
        pullPrices();

        // Product Spinner
        ProductSpinner = findViewById(R.id.Product);
        ProductSpinner.setOnItemSelectedListener(this);

        // Price Spinner
        PriceSpinner = findViewById(R.id.Price);
        PriceSpinner.setOnItemSelectedListener(this);

        // Account Spinner
        AccountSpinner = findViewById(R.id.Account);
        AccountSpinner.setOnItemSelectedListener(this);

        List<String> AccountType = new ArrayList<String>();
        AccountType.add("Keith");
        AccountType.add("Josh");
        AccountType.add("Louis");

        ArrayAdapter<String> AccountAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, AccountType);

        AccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AccountSpinner.setAdapter(AccountAdapter);
        AccountSpinner.setOnItemSelectedListener(this);

        // Quantity Entry box
        Quantity = findViewById(R.id.Quantity);

        Button saveButton;
        Button sendButton;
        Button deleteButton;
        saveButton = findViewById(R.id.SaveButton);
        sendButton = findViewById(R.id.SendButton);
        deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              sendInfo();
                                          }
                                      }
        );

    }

    public void saveInfo() {
        String getQuantity = Quantity.getText().toString();

        Sale sale = new Sale();

        if (getQuantity == null || getQuantity.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            sale.setQuantity(getQuantity);
            sale.setPrice(price);
            sale.setProduct(product);
            sale.setAccount(account);
            salesList.add(sale);
            Log.i(TAG, "sdd Sale created.");
        }

        updateTable();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        if (parent.getId() == R.id.Price){
            price = PriceList.get(position);
        } else if (parent.getId() == R.id.Product){
            product = ProductList.get(position);
        } else if (parent.getId() == R.id.Account){
            account = text;
        }
    }

    public void sendInfo(){
        ArrayList<JsonConvertible> data = (ArrayList<JsonConvertible>)(Object)salesList;
        firedb.storeJson(data, "Sales");
        salesList.clear();
    }

    public void pullProducts(){
        firedb.pullCollection("Products", "com.byui.thf10.Product", new CallBackList() {
            @Override
            public void onCallback(List<Object> jsonList) {
                for (Object i : jsonList) {
                    ProductList.add((Product)i);
                    Log.d(TAG, "Products loaded.");
                }
                Toast.makeText(getApplicationContext(), "Products List Loaded", Toast.LENGTH_SHORT).show();
                populateProducts();
            }
        });
    }

    public void pullPrices(){
        firedb.pullCollection("Prices", "com.byui.thf10.Price", new CallBackList() {
            @Override
            public void onCallback(List<Object> jsonList) {
                for (Object i : jsonList) {
                    PriceList.add((Price) i);
                    Log.d(TAG, "Prices loaded.");
                }
                Toast.makeText(getApplicationContext(), "Prices List Loaded", Toast.LENGTH_SHORT).show();
                populatePrice();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void populateProducts(){
        for (Product i : ProductList){
            Log.d(TAG, "sddd product is converted.");
            String pattern = i.getName();
            ProductType.add(pattern);
            ArrayAdapter<String> ProductAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, ProductType);

            ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ProductSpinner.setAdapter(ProductAdapter);
            ProductSpinner.setOnItemSelectedListener(this);
        }
    }

    private void populatePrice(){
        for (Price i : PriceList){
            String desc = i.getDescription();
            float pric = i.getPrice();

            PriceType.add(desc + " " + pric);

            ArrayAdapter<String> PriceAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, PriceType);
            PriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            PriceSpinner.setAdapter(PriceAdapter);
            PriceSpinner.setOnItemSelectedListener(this);
        }
    }

    private void updateTable() {
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
        int row = 1;
        for (int i = -1; i < salesList.size(); i++) {
            TableRow tr = new TableRow(SalesActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (row == 1) {
                // create text view and headings
                TextView c1 = new TextView(SalesActivity.this);
                c1.setText("Date");
                c1.setTextColor(Color.BLUE);
                c1.setTextSize(15);
                tr.addView(c1);
                TextView c2 = new TextView(SalesActivity.this);
                c2.setPadding(10, 0, 0, 0);
                c2.setTextSize(15);
                c2.setText("Product");
                c2.setTextColor(Color.BLUE);
                tr.addView(c2);
                TextView c3 = new TextView(SalesActivity.this);
                c3.setPadding(15, 0, 0, 0);
                c3.setText("Quantity");
                c3.setTextColor(Color.BLUE);
                c3.setTextSize(15);
                tr.addView(c3);
                TextView c4 = new TextView(SalesActivity.this);
                c4.setPadding(15, 0, 0, 0);
                c4.setText("amount");
                c4.setTextColor(Color.BLUE);
                c4.setTextSize(15);
                TextView c5 = new TextView(SalesActivity.this);
                c5.setPadding(15, 0, 0, 0);
                c5.setText("Sale");
                c5.setTextColor(Color.BLUE);
                c5.setTextSize(15);
                tr.addView(c5);
                tv.addView(tr);
                final View view = new View(SalesActivity.this);
                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.BLUE);
                tv.addView(view); // add line below heading
                row = 0;
            } else {
                Sale sale = salesList.get(i);
                TextView v1 = new TextView(SalesActivity.this);
                String str = String.valueOf(sale.getDate());
                v1.setText(str);
                v1.setTextColor(Color.RED);
                v1.setTextSize(15);
                tr.addView(v1);
                TextView v2 = new TextView(SalesActivity.this);
                v2.setPadding(10, 0, 0, 0);
                v2.setTextSize(15);
                String str1 = sale.getProduct().getName();
                v2.setText(str1);
                v2.setTextColor(Color.RED);
                tr.addView(v2);
                TextView v3 = new TextView(SalesActivity.this);
                v3.setPadding(15, 0, 0, 0);
                String str2 = String.valueOf(sale.getQuantity());
                v3.setText(str2);
                v3.setTextColor(Color.RED);
                v3.setTextSize(15);
                tr.addView(v3);
                TextView v4 = new TextView(SalesActivity.this);
                v4.setPadding(15, 0, 0, 0);
                String str4 = String.valueOf(sale.getPrice().getPrice() * Integer.parseInt(sale.getQuantity()));
                v4.setText(str4);
                v4.setTextColor(Color.RED);
                v4.setTextSize(15);
                tr.addView(v4);
                TextView v5 = new TextView(SalesActivity.this);
                v5.setPadding(15, 0, 0, 0);
                String str5 = sale.getAccount();
                v5.setText(str5);
                v5.setTextColor(Color.RED);
                v5.setTextSize(15);
                tr.addView(v5);
                tv.addView(tr);
                final View view = new View(SalesActivity.this);
                view.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                view.setBackgroundColor(Color.WHITE);
                tv.addView(view);  // add line below each row
            }
        }
    }


}
