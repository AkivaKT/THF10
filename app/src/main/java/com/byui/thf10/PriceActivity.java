package com.byui.thf10;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText NewPrice;
    private Button saveButton;
    private Button sendButton;
    private ArrayList<JsonConvertible> priceList = new ArrayList<>();
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Spinner daySpinner;
    private ArrayAdapter<Integer> lAdapter;
    private ArrayAdapter<Integer> sAdapter;
    private ArrayAdapter<Integer> stAdapter;
    boolean longMonth = true;
    private FireStore firedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        // CONNECTION WITH DATABASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);

        // create spinner
        monthSpinner = findViewById(R.id.month);
        monthSpinner.setOnItemSelectedListener(this);
        daySpinner = findViewById(R.id.day);
        daySpinner.setOnItemSelectedListener(this);
        yearSpinner = findViewById(R.id.year);
        yearSpinner.setOnItemSelectedListener(this);

        // Adapters for months
        Integer[] shortMonth = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28};
        List<Integer> sMonthList = new ArrayList<>(Arrays.asList(shortMonth));
        stAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item , sMonthList);
        Integer[] shorterMonth = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
        List<Integer> stMonthList = new ArrayList<>(Arrays.asList(shorterMonth));
        sAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item , stMonthList);
        Integer[] longMonth = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        List<Integer> lMonthList = new ArrayList<>(Arrays.asList(longMonth));
        lAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item , lMonthList);

        stAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(lAdapter);

        // Edittext
        NewPrice = findViewById(R.id.NewPrice);

        // Buttons
        saveButton = findViewById(R.id.SaveButton);
        sendButton = findViewById(R.id.sendButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                }
            }
        );

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendInfo();
                }
            }
        );
    }

    public void saveInfo() {
        String getNewPrice = NewPrice.getText().toString();
        String year = yearSpinner.getSelectedItem().toString();
        int month = monthSpinner.getSelectedItemPosition();
        String day = daySpinner.getSelectedItem().toString();
        Price price = new Price();

        if (getNewPrice == null || getNewPrice.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Calendar c = Calendar.getInstance();
            Log.i(TAG, "sdd " + Integer.parseInt(year) + month + Integer.parseInt(day) );
            c.set(Integer.parseInt(year), month, Integer.parseInt(day));
            price.setNewPrice(getNewPrice);
            price.setStart_date(c.getTime());
            price.setEnd_date(null);
            priceList.add(price);
            Log.i(TAG, "Price created.");
        }
    }

    public void sendInfo(){
        firedb.storeJson(priceList, "Prices");
        priceList.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String text = parent.getItemAtPosition(position).toString();

        if (parent.getId() == R.id.month){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !longMonth){
                daySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                longMonth = true;
            } else if (position + 1 == 2){
                daySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                longMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    longMonth){
                daySpinner.setAdapter(sAdapter);
                longMonth = false;
            }
        }
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
