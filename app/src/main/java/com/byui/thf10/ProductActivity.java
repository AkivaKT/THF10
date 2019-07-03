package com.byui.thf10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText type;
    private EditText series;
    private EditText Pattern;
    private EditText color1;
    private EditText color2;
    private EditText quanitity;

    private TextView output_TextView;
    private Button button;

    String testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        type = (EditText) findViewById(R.id.typeId);
        series = (EditText) findViewById(R.id.typeSeries);
        Pattern = (EditText) findViewById(R.id.typePattern);
        color1 = (EditText) findViewById(R.id.typeColor1);
        color2 = (EditText) findViewById(R.id.typeColor2);
        quanitity = (EditText) findViewById(R.id.typeQuantity);

        button.setOnClickListener(this);
    }

public void onClick(View v) {
        if (v.getId() == button.getId()) {
            testText = color1.getText().toString();
            output_TextView.setText(testText);
        }
}

}
