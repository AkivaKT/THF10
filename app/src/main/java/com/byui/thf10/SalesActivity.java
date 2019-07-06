package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static android.R.layout.simple_spinner_item;

public class SalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText Quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

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

        // Quantity Value
        //Quantity = (EditText) findViewById(R.id.Quantity);

        // Json Convertible
        Type listType = new TypeToken<List<JsonConvertible>>() {
        }.getType();
        List<JsonConvertible> SaleEntry = new LinkedList<>();
        //SaleEntry.add(ProductSpinner);
        //SaleEntry.add(PriceSpinner);
        //SaleEntry.add(Quantity);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
