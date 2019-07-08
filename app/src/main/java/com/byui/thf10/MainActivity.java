package com.byui.thf10;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private EditText id;
    private EditText password;
    private Button login;
    private TextView info;
    private int loginCounter = 50;
    private List<Account> accounts;
    private FireStore firedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // login
        id = (EditText)findViewById(R.id.typeId);
        password = (EditText)findViewById(R.id.typePassword);
        login = (Button)findViewById(R.id.clickLogin);
        info = (TextView)findViewById(R.id.incorrect);
        info.setText("Remained attemps: 5");
        accounts = new ArrayList<>();

        // title
        setTitle("HandsomeTieFactory");
        // pull list of accounts
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);
        pullAccounts();

        // button listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(id.getText().toString(), password.getText().toString());
            }
        });
    }
    private void validate(String userName, String userPassword) {

        Boolean userExist = true;
        // check id and password in this function.
        for (Account user : accounts) {
            Log.d(TAG, "sdd password is " + user.checkPassword(userPassword) + accounts.size());
            if (user.getUserName().equals(userName)) {
                Log.d(TAG, "sdd User matches with" + userName);
                if (user.checkPassword(userPassword)) {
                    Log.d(TAG, "sdd User and password  matches with" + userName + userPassword);
                    //woo change here LoginActivity to LoginActivity2
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    break;
                } else {
                    Log.d(TAG, "sdd but with wrong password" + userName);
                    loginCounter--;
                    info.setText("Remained attemps: " + String.valueOf(loginCounter));
                    Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
                    userExist = true;
                    if (loginCounter == 0) {
                        login.setEnabled(false);
                    }
                    break;
                }
            } else {
                Log.d(TAG, "sdd Not this user" + user.getUserName());
                userExist = false;
            }
        }
        if (!userExist) {
            loginCounter--;
            info.setText("Remained attemps: " + String.valueOf(loginCounter));
            Toast.makeText(getApplicationContext(), "No such users", Toast.LENGTH_SHORT).show();
            if (loginCounter == 0) {
                login.setEnabled(false);
            }
        }
    }


    public void pullAccounts(){
        firedb.pullCollection("Account", "com.byui.thf10.Account", new CallBackList() {
            @Override
            public void onCallback(List<Object> jsonList) {
                for (Object i : jsonList) {
                    accounts.add((Account) i);
                    Log.d(TAG, "Account loaded.");
                }
                Toast.makeText(getApplicationContext(), "You may login now", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void createAccount(){
        Calendar c1 = Calendar.getInstance();
        c1.set(1994, 4, 12);

        Account a1 = new Account();
        a1.setFirstName("Keith");
        a1.setUserName("admin");
        a1.setPassword("1234");
        try {
            a1.encrypt();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<JsonConvertible> a = new ArrayList<>();
        a.add(a1);
        firedb.storeJson(a, "Account");
    }




}
