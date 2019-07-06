package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.ContentValues.TAG;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private EditText type;
    private EditText series;
    private EditText pattern;
    private EditText color1;
    private EditText color2;

    private TextView output_TextView;
    Button button;

    private ArrayList<JsonConvertible> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        type = findViewById(R.id.NewPrice);
        series = findViewById(R.id.typeSeries);
        pattern = findViewById(R.id.typePattern);
        color1 = findViewById(R.id.typeColor1);
        color2 = findViewById(R.id.typeColor2);
        // quantity variable
        // quantitySpinner = (Spinner) findViewById(R.id.Quantity);
        button = findViewById(R.id.clickButton);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });
        //List<String> Listseries = new ArrayList<String>();
       // List<String> Listpattern = new ArrayList<String>();
        //List<String> Listcolor1 = new ArrayList<String>();
       // List<String> Listcolor2 = new ArrayList<String>();
       // List<String> Listquanitity = new ArrayList<String>();
    }

    public void saveInfo() {
        String getType = type.getText().toString();
        String getSeries = series.getText().toString();
        String getPattern = pattern.getText().toString();
        String getColor1 = color1.getText().toString();
        String getColor2 = color2.getText().toString();

        Product product = new Product();

        if (getType == null || getType.trim().equals("") || getSeries == null || getSeries.trim().equals("") || getPattern == null || getPattern.trim().equals("") || getColor1 == null || getColor1.trim().equals("") || getColor2 == null || getColor2.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            product.setType(getType);
            product.setSeries(getSeries);
            product.setPattern(getPattern);
            product.setColor1(getColor1);
            product.setColor2(getColor2);
            // something add quantity
            // product.setQuantity(quantity);

            // do not work!!!!!!!
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
            //button.setAdapter(adapter);
            productList.add(product);
            Log.i(TAG, "sdd Product created.");
        }
    }
}
