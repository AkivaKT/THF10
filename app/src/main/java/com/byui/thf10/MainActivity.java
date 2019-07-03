package com.byui.thf10;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private EditText id;
    private EditText password;
    private Button login;
    private TextView info;
    private int loginCounter = 5;
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

        // title
        setTitle("HandsomeTieFactory");

        //createAccount();

        // pull list of accounts
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firedb = new FireStore(db);
        List<JsonConvertible> json = pullAccounts();
        if (json.isEmpty()){
            Log.d(TAG, "empty accounts list" );
        }


        // button listener
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(id.getText().toString(), password.getText().toString());
            }
        });
    }
    private void validate(String userName, String userPassword) {
        // check id and password in this function.



        if ((userName.equals("Admin")) && (userPassword.equals("1234"))) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else {
            loginCounter--;

            info.setText("Remained attemps: " + String.valueOf(loginCounter));

            if (loginCounter == 0) {
                login.setEnabled(false);
            }
        }
    }

    public void createAccount(){
        Account a1 = new Account();
        a1.setFirstName("Keith2");
        a1.setLastName("Tung2");
        a1.setUserName("akin2");
        a1.setPassword("newPass");

        List<JsonConvertible> l1 = new LinkedList<>();
        l1.add(a1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FireStore f = new FireStore(db);
        f.setup();
        f.storeJson(l1);

    }

    public List<JsonConvertible> pullAccounts(){
        return firedb.pullCollection("Account");
    }
}
