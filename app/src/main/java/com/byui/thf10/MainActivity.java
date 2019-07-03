package com.byui.thf10;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        // check id and password in this function.

        for (Account user : accounts) {
            if (userName.equals(user.getUserName())) {
                if (user.checkPassword(userPassword)) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                } else {
                    loginCounter--;
                    info.setText("Remained attemps: " + String.valueOf(loginCounter));
                    Toast.makeText(getApplicationContext(),"Wrong password",Toast.LENGTH_SHORT).show();
                    if (loginCounter == 0) {
                        login.setEnabled(false);
                    }
                }
            } else {
                loginCounter--;

                info.setText("Remained attemps: " + String.valueOf(loginCounter));
                Toast.makeText(getApplicationContext(),"No such users",Toast.LENGTH_SHORT).show();
                if (loginCounter == 0) {
                    login.setEnabled(false);
                }
            }
        }
    }


    public void pullAccounts(){
        firedb.pullCollection("Account", new CallBackList() {
            @Override
            public void onCallback(List<JsonConvertible> jsonList) {
                accounts = (List<Account>)(List<?>)jsonList;
                Toast.makeText(getApplicationContext(), "You may login now", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
