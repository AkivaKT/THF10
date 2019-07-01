package com.byui.thf10;


import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    private EditText id;
    private EditText password;
    private Button login;
    private TextView info;
    private int loginCounter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        id = (EditText)findViewById(R.id.typeId);
        password = (EditText)findViewById(R.id.typePassword);
        login = (Button)findViewById(R.id.clickLogin);
        info = (TextView)findViewById(R.id.incorrect);

        info.setText("Remained attemps: 5");
        setTitle("HansomeTieFactory");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(id.getText().toString(), password.getText().toString());
            }
        });
    }
    private void validate(String userName, String userPassword) {
        // check id and password in this function.
        //
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

    public void successLogin(View theButton) {

    }
}
