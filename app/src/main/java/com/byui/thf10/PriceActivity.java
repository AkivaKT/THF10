package com.byui.thf10;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PriceActivity extends AppCompatActivity {

    private EditText NewPrice;

    private TextView output_TextView;
    Button button;

    private ArrayList<JsonConvertible> priceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        NewPrice = findViewById(R.id.NewPrice);
        // quantity variable
        // quantitySpinner = (Spinner) findViewById(R.id.Quantity);
        button = findViewById(R.id.clickButton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

    }

    public void saveInfo() {
        String getNewPrice = NewPrice.getText().toString();

        Price price = new Price();

        if (getNewPrice == null || getNewPrice.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            price.setNewPrice(getNewPrice);
            // something add quantity
            // product.setQuantity(quantity);

            // do not work!!!!!!!
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            //button.setAdapter(adapter);
            priceList.add(price);
            Log.i(TAG, "sdd Product created.");
        }
    }
}
