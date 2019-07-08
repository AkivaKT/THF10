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

import static android.content.ContentValues.TAG;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText type;
    private EditText series;
    private EditText pattern;
    private EditText color1;
    private EditText color2;
    private FireStore firedb;


    private TextView output_TextView;
    private Button SaveButton;
    private Button sendButton;


    private ArrayList<JsonConvertible> productList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // CONNECTION WITH DATABASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);

        type = findViewById(R.id.NewPrice);
        series = findViewById(R.id.typeSeries);
        pattern = findViewById(R.id.typePattern);
        color1 = findViewById(R.id.typeColor1);
        color2 = findViewById(R.id.typeColor2);
        SaveButton = findViewById(R.id.SaveButton);
        sendButton = findViewById(R.id.SendButton);


        Spinner spinner = findViewById(R.id.Quantity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);



        SaveButton.setOnClickListener(new View.OnClickListener() {
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
            productList.add(product);
            Log.i(TAG, "sdd Product created.");
        }
    }

    public void sendInfo(){
        firedb.storeJson(productList, "Products");
        productList.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
