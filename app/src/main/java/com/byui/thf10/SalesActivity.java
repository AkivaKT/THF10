package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private Spinner MonthSpinner;
    private Spinner YearSpinner;
    private Spinner DaySpinner;
    private ArrayAdapter<Integer> lAdapter;
    private ArrayAdapter<Integer> sAdapter;
    private ArrayAdapter<Integer> stAdapter;
    boolean longMonth = true;
    Context context;

    public SalesActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        context = this;

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

        //date spinners
        MonthSpinner = findViewById(R.id.Month);
        MonthSpinner.setOnItemSelectedListener(this);
        DaySpinner = findViewById(R.id.Day);
        DaySpinner.setOnItemSelectedListener(this);
        YearSpinner = findViewById(R.id.Year);
        YearSpinner.setOnItemSelectedListener(this);

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
        DaySpinner.setAdapter(lAdapter);

        // buttons
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


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

    }

    public void saveInfo() {
        String getQuantity = Quantity.getText().toString();
        Sale sale          = new Sale();
        String year       = YearSpinner.getSelectedItem().toString();
        int month         = MonthSpinner.getSelectedItemPosition();
        String day        = DaySpinner.getSelectedItem().toString();

        if (getQuantity == null || getQuantity.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        else {
            LocalDate localDate = LocalDate.of(Integer.parseInt(year), month + 1, Integer.parseInt(day));
            sale.setDate(localDate.toString());
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

        if (parent.getId() == R.id.eMonth){
            if (Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    !longMonth){
                DaySpinner.setAdapter(lAdapter);
                Log.i(TAG,"sdd day limit + changed");
                longMonth = true;
            } else if (position + 1 == 2){
                DaySpinner.setAdapter(stAdapter);
                Log.i(TAG,"sdd day limit - changed");
                longMonth = false;
            } else if (!Arrays.asList(1, 3, 5, 7, 8, 10, 12).contains(position + 1) &&
                    longMonth){
                DaySpinner.setAdapter(sAdapter);
                longMonth = false;
            }
        }
    }

    public void sendInfo(){
        ArrayList<JsonConvertible> data = (ArrayList<JsonConvertible>)(Object)salesList;
        firedb.storeJson(data, "Sales");
        salesList.clear();
        sendButtonNotification();
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
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
                c4.setText("Amount");
                c4.setTextColor(Color.BLUE);
                c4.setTextSize(15);
                tr.addView(c4);
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
                String str4 = "$" + (sale.getPrice().getPrice() * Integer.parseInt(sale.getQuantity()));
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

    /***
     * send notification.
     */
    public void sendButtonNotification() {
        // Channel ID is arbitrary and only used on API level 26 and higher
        String channel_id = "price.sendButton.notifications.NOTIFICATION_CHANNEL";

        // NotificationChannel must be used for API level 26 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id,
                    channel_id, NotificationManager.IMPORTANCE_HIGH);  // Decided to have channel id and name the same
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Send data to database.")
                .setContentText("Just sent a price data.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, mBuilder.build()); // 0 was arbitrary
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select the sale to delete:");
        final int index = salesList.size();
        String[] saleString = new String[index];
        final boolean[] checks = new boolean[index];
        for (int i = 0; i < salesList.size(); i++){
            Sale p = salesList.get(i);
            saleString[i] = (p.getDate() + " " + p.getProduct().getName() + " $" +
                    Integer.parseInt(p.getQuantity()) * p.getPrice().getPrice());
            checks[i] = false;
        }
        builder.setMultiChoiceItems(saleString, checks, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checks[which] = isChecked;
            }
        });

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (salesList.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Empty data", Toast.LENGTH_LONG).show();
                }
                else {
                    int j = 0;
                    for (int i = 0; i < index; i++) {
                        if (checks[i]){
                            salesList.remove(j);
                        } else{
                            j++;
                        }
                    }
                    updateTable();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
