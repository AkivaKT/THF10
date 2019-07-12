package com.byui.thf10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private Button product;
    private Button price;
    private Button sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Product pr1 = new Product();

        pr1.setQuantity(1);
        pr1.setColor1("yellow");
        pr1.setColor2("green");
        pr1.setPattern("stripe");
        pr1.setSeries("idunno");
        pr1.setType("idunno");

        Product pr2 = new Product();
        pr2.setQuantity(1);
        pr2.setColor1("white");
        pr2.setColor2("brown");
        pr2.setPattern("fill");
        pr2.setSeries("idunno");
        pr2.setType("idunno");

        Product pr3 = new Product();
        pr3.setQuantity(1);
        pr3.setColor1("purple");
        pr3.setColor2("blue");
        pr3.setPattern("Floral");
        pr3.setSeries("idunno");
        pr3.setType("idunno");
        //woo finishes the function above



        Type listType = new TypeToken<List<JsonConvertible>>() {}.getType();
        List<JsonConvertible> target = new LinkedList<>();
        target.add(pr1);
        target.add(pr2);
        target.add(pr3);



        /*
        CollectionReference myRef = database.collection("Price");
        Map<String, Object> Update = new HashMap<>();

        Update.put("Product", "10.00");

        myRef.add(Update)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot successfully written!" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

*/
        //button = (Button) findViewById(R.id.button);
        product = (Button) findViewById(R.id.product);
        price = (Button) findViewById(R.id.price);
        sale = (Button) findViewById(R.id.sale);

       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();

            }
        });*/

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();

            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();

            }
        });

        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity5();

            }
        });
    }

    /*public void openActivity2(){
        //Intent intent = new Intent(this, woo_test.class);
        //startActivity(intent);
    }*/

    public void openActivity3(){
        Intent productIntent = new Intent(this, LoginActivity3.class);
        startActivity(productIntent);
    }

    public void openActivity4(){
        Intent priceIntent = new Intent(this, LoginActivity2.class);
        startActivity(priceIntent);
    }

    public void openActivity5(){

        Intent intent3 = new Intent(this, LoginActivity4.class);
        startActivity(intent3);
    }

}
