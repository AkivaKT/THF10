package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

    private EditText Price;
    private EditText Product;
    private EditText Quantity;
    private FireStore firedb;
    private ArrayList<JsonConvertible> salesList = new ArrayList<>();
    private Button saveButton;
    private Button sendButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // Product Spinner
        Product spinner;
        Spinner ProductSpinner = findViewById(R.id.Product);
        ProductSpinner.setOnItemSelectedListener(this);

        List<String> ProductType = new ArrayList<String>();
        ProductType.add("Floral");
        ProductType.add("Solid");
        ProductType.add("Polka");
        ProductType.add("Paisley");
        ProductType.add("University");
        ProductType.add("Plaid");

        ArrayAdapter<String> ProductAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ProductType);

        ProductAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ProductSpinner.setAdapter(ProductAdapter);
        ProductSpinner.setOnItemSelectedListener(this);

        // Price Spinner
        Price Spinner;
        Spinner PriceSpinner = findViewById(R.id.Price);
        PriceSpinner.setOnItemSelectedListener(this);

        List<Float> PriceType = new ArrayList<Float>();
        PriceType.add((float) 13.50);
        PriceType.add((float) 12.50);
        PriceType.add((float) 11.50);
        PriceType.add((float) 10.50);
        PriceType.add((float) 9.50);
        PriceType.add((float) 8.50);

        ArrayAdapter<Float> PriceAdapter = new ArrayAdapter<Float>(this,
                android.R.layout.simple_spinner_item, PriceType);
        PriceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PriceSpinner.setAdapter(PriceAdapter);
        PriceSpinner.setOnItemSelectedListener(this);

        // Quantity Entry box
        Quantity = findViewById(R.id.Quantity);

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
        //String getPrice = Price.getText().toString();
        //String getProduct = Product.getText().toString();

        Sale sale = new Sale();

        if (getQuantity == null || getQuantity.trim().equals("")){ //|| getPrice == null || getPrice.trim().equals("") || getProduct == null || getProduct.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            sale.setQuantity(getQuantity);
            //sale.setPrice(getPrice);
            //sale.setProduct(getProduct);
            salesList.add(sale);
            Log.i(TAG, "sdd Sale created.");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void sendInfo(){
        firedb.storeJson(salesList, "Sales");
        salesList.clear();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
