package com.byui.thf10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

public class PriceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText newPrice;
    private EditText description;
    private ArrayList<Price> priceList = new ArrayList<>();
    private Spinner startMonthSpinner;
    private Spinner startYearSpinner;
    private Spinner startDaySpinner;
    private Spinner endMonthSpinner;
    private Spinner endYearSpinner;
    private Spinner endDaySpinner;
    private ArrayAdapter<Integer> lAdapter;
    private ArrayAdapter<Integer> sAdapter;
    private ArrayAdapter<Integer> stAdapter;
    boolean slongMonth = true;
    boolean elongMonth = true;
    private FireStore fireDb;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        context = this;

        // CONNECTION WITH DATABASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fireDb = new FireStore(db);

        // create spinner
        setupSpinner();
        // Edit text
        newPrice = findViewById(R.id.newPrice);
        description = findViewById(R.id.description);

        // Buttons
        Button saveButton = findViewById(R.id.saveButton);
        Button sendButton = findViewById(R.id.sendButton);
        Button showButton = findViewById(R.id.showButton);
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

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    /**
     * Save price data (1)
     * <p>
     * Activated by the saveButton, to take data from the spinners and edittexts to for (2)
     * an array list of (@Price) saved in PriceList, This data will be display in the
     * Table view on the same activity.
     * </p>
     * @author Keith Tung
     */
    public void saveInfo() {
        String getNewPrice = newPrice.getText().toString();
        String sYear       = startYearSpinner.getSelectedItem().toString();
        int sMonth         = startMonthSpinner.getSelectedItemPosition();
        String sDay        = startDaySpinner.getSelectedItem().toString();
        String eYear       = endYearSpinner.getSelectedItem().toString();
        int eMonth         = endMonthSpinner.getSelectedItemPosition();
        String eDay        = endDaySpinner.getSelectedItem().toString();
        String descrip     = description.getText().toString();
        Price price        = new Price();

        if (getNewPrice == null || getNewPrice.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            LocalDate localDate = LocalDate.of(Integer.parseInt(sYear), sMonth + 1, Integer.parseInt(sDay));
            Log.i(TAG, "data: " + Integer.parseInt(sYear) + sMonth + Integer.parseInt(sDay) );
            //price.setAmount(Float.parseFloat(getNewPrice));
            price.setStart_date(localDate.toString());
            localDate = LocalDate.of(Integer.parseInt(eYear), eMonth + 1, Integer.parseInt(eDay));
            price.setEnd_date(localDate.toString());
            priceList.add(price);
            price.setActive(true);
            price.setDescription(descrip);
            Log.i(TAG, "New Price created.");
            updateTable();
        }
    }

    /**
     * Send price data (1)
     * <p>
     * Activated by the sendButton, to take the Price from PriceList and send it to firebase, (2)
     * it will clear PriceList and the Table view will be renewed/emptied.
     * </p>
     * @author Keith Tung
     */
    public void sendInfo(){
        ArrayList<JsonConvertible> data = (ArrayList<JsonConvertible>)(Object)priceList;
        fireDb.storeJson(data, "Prices");
        Toast.makeText(this ,"Total of " + priceList.size() + " items saved.", Toast.LENGTH_SHORT).show();
        priceList.clear();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("Table needs renewal"));
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
    }

    /**
     * Save price data (1)
     * <p>
     * Activated by the saveButton, to take data from the spinners and edittexts to for (2)
     * an array list of (@Price) saved in PriceList, This data will be display in the
     * Table view on the same activity.
     * </p>
     * @author Keith Tung
     */
    public void setupSpinner(){
        startMonthSpinner = findViewById(R.id.sMonth);
        startMonthSpinner.setOnItemSelectedListener(this);
        startDaySpinner = findViewById(R.id.sDay);
        startDaySpinner.setOnItemSelectedListener(this);
        startYearSpinner = findViewById(R.id.sYear);
        startYearSpinner.setOnItemSelectedListener(this);

        endMonthSpinner = findViewById(R.id.eMonth);
        endMonthSpinner.setOnItemSelectedListener(this);
        endDaySpinner = findViewById(R.id.eDay);
        endDaySpinner.setOnItemSelectedListener(this);
        endYearSpinner = findViewById(R.id.eYear);
        endYearSpinner.setOnItemSelectedListener(this);

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
        startDaySpinner.setAdapter(lAdapter);
        endDaySpinner.setAdapter(lAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String text = parent.getItemAtPosition(position).toString();

        if (parent.getId() == R.id.sMonth){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !slongMonth){
                startDaySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                slongMonth = true;
            } else if (position + 1 == 2){
                startDaySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                slongMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    slongMonth){
                startDaySpinner.setAdapter(sAdapter);
                slongMonth = false;
            }
        }


        if (parent.getId() == R.id.eMonth){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !elongMonth){
                endDaySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                elongMonth = true;
            } else if (position + 1 == 2){
                endDaySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                elongMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    elongMonth){
                endDaySpinner.setAdapter(sAdapter);
                elongMonth = false;
            }
        }
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void updateTable() {
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
        int row = 1;
        for (int i = -1; i < priceList.size(); i++) {
            TableRow tr = new TableRow(PriceActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (row == 1) {
                // create text view and headings
                TextView c1 = new TextView(PriceActivity.this);
                c1.setText("Start Date");
                c1.setTextColor(Color.BLUE);
                c1.setTextSize(15);
                tr.addView(c1);
                TextView c2 = new TextView(PriceActivity.this);
                c2.setPadding(10, 0, 0, 0);
                c2.setTextSize(15);
                c2.setText("End Date");
                c2.setTextColor(Color.BLUE);
                tr.addView(c2);
                TextView c3 = new TextView(PriceActivity.this);
                c3.setPadding(15, 0, 0, 0);
                c3.setText("Price tag");
                c3.setTextColor(Color.BLUE);
                c3.setTextSize(15);
                tr.addView(c3);
                TextView c4 = new TextView(PriceActivity.this);
                c4.setPadding(15, 0, 0, 0);
                c4.setText("Description");
                c4.setTextColor(Color.BLUE);
                c4.setTextSize(15);
                tr.addView(c4);
                tv.addView(tr);
                final View view = new View(PriceActivity.this);
                view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                view.setBackgroundColor(Color.BLUE);
                tv.addView(view); // add line below heading
                row = 0;
            } else {
                Price price = priceList.get(i);
                TextView v1 = new TextView(PriceActivity.this);
                String str = String.valueOf(price.getStart_date());
                v1.setText(str);
                v1.setTextColor(Color.RED);
                v1.setTextSize(15);
                tr.addView(v1);
                TextView v2 = new TextView(PriceActivity.this);
                v2.setPadding(10, 0, 0, 0);
                v2.setTextSize(15);
                String str1 = price.getEnd_date();
                v2.setText(str1);
                v2.setTextColor(Color.RED);
                tr.addView(v2);
                TextView v3 = new TextView(PriceActivity.this);
                v3.setPadding(15, 0, 0, 0);
                String str2 = String.valueOf(price.getPrice());
                v3.setText(str2);
                v3.setTextColor(Color.RED);
                v3.setTextSize(15);
                tr.addView(v3);
                TextView v4 = new TextView(PriceActivity.this);
                v4.setPadding(15, 0, 0, 0);
                String str4 = price.getDescription();
                v4.setText(str2);
                v4.setTextColor(Color.RED);
                v4.setTextSize(15);
                tr.addView(v4);
                tv.addView(tr);
                final View view = new View(PriceActivity.this);
                view.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                view.setBackgroundColor(Color.WHITE);
                tv.addView(view);  // add line below each row
            }
        }
    }

    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select the price to delete:");
        final int index = priceList.size();
        String[] priceString = new String[index];
        final boolean[] checks = new boolean[index];
        for (int i = 0; i < priceList.size(); i++){
            Price p = priceList.get(i);
            priceString[i] = ("$ " + p.getPrice() + " " + p.getDescription());
            checks[i] = false;
        }
        builder.setMultiChoiceItems(priceString, checks, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checks[which] = isChecked;
            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int j = 0;
                for (int i = 0; i < index; i++){
                    if (checks[i]){
                        priceList.remove(j);
                    } else{
                        j++;
                    }
                }
                updateTable();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
