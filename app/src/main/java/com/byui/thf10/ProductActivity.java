package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import static android.content.ContentValues.TAG;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText type;
    private EditText series;
    private EditText name;
    private EditText color1;
    private EditText color2;
    private Spinner spinner;

    private FireStore firedb;
    Context context;

    private Button saveButton;
    private Button sendButton;
    private Button deleteButton;

    private ArrayList<Product> productList = new ArrayList<>();

    /**
     * The inputs for this activity is a combination of a spinner and text boxes.
     * The spinner is for the number of inventory or "quantity" that the company has in stock.
     * The other input text boxes are there for entry of patterns and colors.
     * The delete button is there in case of mistakes.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // CONNECTION WITH DATABASE
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);
        context = this;


        type = findViewById(R.id.typeType);
        series = findViewById(R.id.typeSeries);
        name = findViewById(R.id.typeName);
        color1 = findViewById(R.id.typeColor1);
        color2 = findViewById(R.id.typeColor2);
        saveButton = findViewById(R.id.SaveButton);
        sendButton = findViewById(R.id.SendButton);
        deleteButton = findViewById(R.id.deleteButton);

        spinner = findViewById(R.id.Quantity);



        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
                if (!productList.isEmpty()) {
                    saveButtonNotification();
                }
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
                                              String selectedItem = spinner.getSelectedItem().toString();
                                              deleteObject(selectedItem);
                                          }
                                      }
        );
    }


    /***
     * save information in firebase of variables and make to string so that we can use in the Android studio.
     * Eventually, we are going to cover to Json.
     */
    public void saveInfo() {
        String getType = type.getText().toString();
        String getSeries = series.getText().toString();
        String getColor1 = color1.getText().toString();
        String getColor2 = color2.getText().toString();
        String getName = name.getText().toString();
        String getQuantity = spinner.getSelectedItem().toString();

        Product product = new Product();
        /// if text field is empty, toast a error message.
        if (getType == null || getType.trim().equals("") || getSeries == null || getSeries.trim().equals("") || getName == null || getName.trim().equals("") || getColor1 == null || getColor1.trim().equals("") || getColor2 == null || getColor2.trim().equals(""))  {
            Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
        }
        /// if there is no error, put each input to the product list.
        else {
            product.setType(getType);
            product.setSeries(getSeries);
            product.setName(getName);
            product.setColor1(getColor1);
            product.setColor2(getColor2);
            product.setQuantity(getQuantity);

            productList.add(product);
            Log.i(TAG, "sdd Product created.");
            updateTable();
        }
    }

    /***
     * save notification
     */
    public void saveButtonNotification() {
        // Channel ID is arbitrary and only used on API level 26 and higher
        String channel_id = "product.saveButton.notifications.NOTIFICATION_CHANNEL";

        // NotificationChannel must be used for API level 26 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id,
                    channel_id, NotificationManager.IMPORTANCE_HIGH);  // Decided to have channel id and name the same
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Saved data to database.")
                .setContentText("Just sent a product data.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, mBuilder.build()); // 0 was arbitrary
    }

    /***
     * send information from application to database(firebase). If there is no data, show error message.
     */
    public void sendInfo(){
        if (productList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Need to save data first then send it", Toast.LENGTH_LONG).show();
        }
        else {
            ArrayList<JsonConvertible> data = (ArrayList<JsonConvertible>)(Object) productList;
            firedb.storeJson(data, "Products");
            productList.clear();
            sendButtonNotification();
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("Table needs renewal"));
            TableLayout tv = findViewById(R.id.table);
            tv.removeAllViewsInLayout();
        }
    }

    /***
     * send notification.
     */
    public void sendButtonNotification() {
        // Channel ID is arbitrary and only used on API level 26 and higher
        String channel_id = "product.sendButton.notifications.NOTIFICATION_CHANNEL";

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
                .setContentText("Just sent a product data.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, mBuilder.build()); // 0 was arbitrary
    }

    /***
     * delete selected object
     * @param selectedItem
     */
    public void deleteObject(String selectedItem) {
        if (productList.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty data", Toast.LENGTH_LONG).show();
        }
        else {
            showDeleteDialog();
            updateTable();
            deleteButtonNotification();
        }
    }

    /***
     * delete button notification.
     */
    public void deleteButtonNotification() {
        // Channel ID is arbitrary and only used on API level 26 and higher
        String channel_id = "product.deleteButton.notifications.NOTIFICATION_CHANNEL";

        // NotificationChannel must be used for API level 26 and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id,
                    channel_id, NotificationManager.IMPORTANCE_HIGH);  // Decided to have channel id and name the same
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Delete data from database.")
                .setContentText("Just delete selected item.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, mBuilder.build()); // 0 was arbitrary
    }


    /***
     * onItemSelected function. Interface definition for a callback to be invoked when an item in this view has been selected.
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /***
     * update table. If there is any update by clicking one of buttons, update table on the product activity.
     */
    private void updateTable() {
        TableLayout tv = findViewById(R.id.table);
        tv.removeAllViewsInLayout();
        int row = 1;
        for (int i = -1; i < productList.size(); i++) {
            TableRow tr = new TableRow(ProductActivity.this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // this will be executed once
            if (row == 1) {
                // create text view and headings
                TextView c1 = new TextView(ProductActivity.this);
                c1.setText("Name");
                c1.setTextColor(Color.BLUE);
                c1.setTextSize(15);
                tr.addView(c1);

                TextView c2 = new TextView(ProductActivity.this);
                c2.setPadding(10, 0, 0, 0);
                c2.setTextSize(15);
                c2.setText("Series");
                c2.setTextColor(Color.BLUE);
                tr.addView(c2);

                TextView c3 = new TextView(ProductActivity.this);
                c3.setPadding(10, 0, 0, 0);
                c3.setText("Type");
                c3.setTextColor(Color.BLUE);
                c3.setTextSize(15);
                tr.addView(c3);

                TextView c4 = new TextView(ProductActivity.this);
                c4.setPadding(10, 0, 0, 0);
                c4.setTextSize(15);
                c4.setText("Primary Color");
                c4.setTextColor(Color.BLUE);
                tr.addView(c4);

                TextView c5 = new TextView(ProductActivity.this);
                c5.setPadding(10, 0, 0, 0);
                c5.setTextSize(15);
                c5.setText("Secondary Color");
                c5.setTextColor(Color.BLUE);
                tr.addView(c5);

                TextView c6 = new TextView(ProductActivity.this);
                c6.setPadding(10, 0, 0, 0);
                c6.setTextSize(15);
                c6.setText("Quantity");
                c6.setTextColor(Color.BLUE);
                tr.addView(c6);

                tv.addView(tr);
                final View view = new View(ProductActivity.this);
                view.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 10));
                view.setBackgroundColor(Color.BLUE);
                tv.addView(view); // add line below heading
                row = 0;
            } else {
                Product product = productList.get(i);

                TextView v1 = new TextView(ProductActivity.this);
                v1.setTextSize(15);
                String str = product.getName();
                v1.setText(str);
                v1.setTextColor(Color.RED);
                tr.addView(v1);

                TextView v2 = new TextView(ProductActivity.this);
                v2.setPadding(10, 0, 0, 0);
                v2.setTextSize(15);
                String str1 = product.getSeries();
                v2.setText(str1);
                v2.setTextColor(Color.RED);
                tr.addView(v2);

                TextView v3 = new TextView(ProductActivity.this);
                v3.setPadding(10, 0, 0, 0);
                String str2 = product.getType();
                v3.setText(str2);
                v3.setTextColor(Color.RED);
                v3.setTextSize(15);
                tr.addView(v3);

                TextView v4 = new TextView(ProductActivity.this);
                v4.setPadding(10, 0, 0, 0);
                String str3 = product.getColor1();
                v4.setText(str3);
                v4.setTextColor(Color.RED);
                v4.setTextSize(15);
                tr.addView(v4);

                TextView v5 = new TextView(ProductActivity.this);
                v5.setPadding(10, 0, 0, 0);
                String str4 = product.getColor2();
                v5.setText(str4);
                v5.setTextColor(Color.RED);
                v5.setTextSize(15);
                tr.addView(v5);

                TextView v6 = new TextView(ProductActivity.this);
                v6.setPadding(10, 0, 0, 0);
                String quantity = product.getQuantity();
                v6.setText(quantity);
                v6.setTextColor(Color.RED);
                v6.setTextSize(15);
                tr.addView(v6);

                tv.addView(tr);
                final View view = new View(ProductActivity.this);
                view.setLayoutParams(new
                        TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 10));
                view.setBackgroundColor(Color.WHITE);
                tv.addView(view);  // add line below each row
            }
        }
    }

    /***
     * Show delete dialog. This shows pop up and will alert the user that
     * the selected product hs been deleted form the list. This is in case of
     * errors or mistakes entered.
     */
    private void showDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select the product to delete:");
        final int index = productList.size();
        String[] productString = new String[index];
        final boolean[] checks = new boolean[index];
        for (int i = 0; i < productList.size(); i++){
            Product pro = productList.get(i);
            productString[i] = (pro.getName());
            checks[i] = false;
        }
        builder.setMultiChoiceItems(productString, checks, new DialogInterface.OnMultiChoiceClickListener() {
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
                        productList.remove(j);
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
