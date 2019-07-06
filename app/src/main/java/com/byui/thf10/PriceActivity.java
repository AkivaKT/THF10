package com.byui.thf10;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText NewPrice;
    private TextView output_TextView;
    Button button;
    private ArrayList<JsonConvertible> priceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        Spinner monthSpinner = findViewById(R.id.month);
        monthSpinner.setOnItemSelectedListener(this);


        NewPrice = findViewById(R.id.NewPrice);
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
            // do not work!!!!!!!
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            //button.setAdapter(adapter);
            priceList.add(price);
            Log.i(TAG, "sdd Product created.");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
