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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText newPrice;
    private EditText description;
    private Button saveButton;
    private Button sendButton;
    private ArrayList<JsonConvertible> priceList = new ArrayList<>();
    private Spinner smonthSpinner;
    private Spinner syearSpinner;
    private Spinner sdaySpinner;
    private Spinner emonthSpinner;
    private Spinner eyearSpinner;
    private Spinner edaySpinner;
    private ArrayAdapter<Integer> lAdapter;
    private ArrayAdapter<Integer> sAdapter;
    private ArrayAdapter<Integer> stAdapter;
    boolean slongMonth = true;
    boolean elongMonth = true;
    private FireStore firedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        // CONNECTION WITH DATABASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);

        // create spinner
        smonthSpinner = findViewById(R.id.sMonth);
        smonthSpinner.setOnItemSelectedListener(this);
        sdaySpinner = findViewById(R.id.sDay);
        sdaySpinner.setOnItemSelectedListener(this);
        syearSpinner = findViewById(R.id.sYear);
        syearSpinner.setOnItemSelectedListener(this);

        emonthSpinner = findViewById(R.id.eMonth);
        emonthSpinner.setOnItemSelectedListener(this);
        edaySpinner = findViewById(R.id.eDay);
        edaySpinner.setOnItemSelectedListener(this);
        eyearSpinner = findViewById(R.id.eYear);
        eyearSpinner.setOnItemSelectedListener(this);

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
        sdaySpinner.setAdapter(lAdapter);
        edaySpinner.setAdapter(lAdapter);

        // Edit text
        newPrice = findViewById(R.id.NewPrice);
        description = findViewById(R.id.description);

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
        String getNewPrice = newPrice.getText().toString();
        String sYear = syearSpinner.getSelectedItem().toString();
        int sMonth = smonthSpinner.getSelectedItemPosition();
        String sDay = sdaySpinner.getSelectedItem().toString();
        String eYear = eyearSpinner.getSelectedItem().toString();
        int eMonth = emonthSpinner.getSelectedItemPosition();
        String eDay = edaySpinner.getSelectedItem().toString();
        String descrip = description.getText().toString();
        Price price = new Price();

        if (getNewPrice == null || getNewPrice.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            LocalDate localDate = LocalDate.of(Integer.parseInt(sYear), sMonth + 1, Integer.parseInt(sDay));
            Log.i(TAG, "sdd " + Integer.parseInt(sYear) + sMonth + Integer.parseInt(sDay) );
            price.setAmount(Float.parseFloat(getNewPrice));
            price.setStart_date(localDate.toString());
            localDate = LocalDate.of(Integer.parseInt(eYear), eMonth + 1, Integer.parseInt(eDay));
            price.setEnd_date(localDate.toString());
            priceList.add(price);
            price.setActive(true);
            price.setDescription(descrip);
            Log.i(TAG, "New Price created.");
        }
    }

    public void sendInfo(){
        firedb.storeJson(priceList, "Prices");
        Toast.makeText(this ,"Total of " + priceList.size() + " items saved.", Toast.LENGTH_SHORT).show();
        priceList.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String text = parent.getItemAtPosition(position).toString();

        if (parent.getId() == R.id.sMonth){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !slongMonth){
                sdaySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                slongMonth = true;
            } else if (position + 1 == 2){
                sdaySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                slongMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    slongMonth){
                sdaySpinner.setAdapter(sAdapter);
                slongMonth = false;
            }
        }


        if (parent.getId() == R.id.eMonth){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !elongMonth){
                edaySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                elongMonth = true;
            } else if (position + 1 == 2){
                edaySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                elongMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    elongMonth){
                edaySpinner.setAdapter(sAdapter);
                elongMonth = false;
            }
        }
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
