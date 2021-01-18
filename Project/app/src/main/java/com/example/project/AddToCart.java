package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddToCart extends Activity {
    Button increment,decrement,addtocart;
    TextView quantity;
    EditText nama,harga;
    int buy;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        buy = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtocart);
        setTitle("Add Item To Cart");

        increment = findViewById(R.id.btn_increment);
        decrement = findViewById(R.id.btn_decrement);
        addtocart = findViewById(R.id.btn_updateItem);
        quantity = findViewById(R.id.quantity);
        nama = findViewById(R.id.et_nama);
        harga = findViewById(R.id.et_harga);

        Intent intent = getIntent();
        nama.setText(intent.getStringExtra("nama"));
        harga.setText(intent.getStringExtra("harga"));



        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy++;
                quantity.setText(String.valueOf(buy));
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buy == 0) {
                    Toast.makeText(com.example.project.AddToCart.this, "Cant Below 0", Toast.LENGTH_SHORT).show();
                } else {
                    buy--;
                    quantity.setText(String.valueOf(buy));
                }
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buy == 0) {
                    Toast.makeText(com.example.project.AddToCart.this, "Cannot Add To Cart\nAt Least 1 Item", Toast.LENGTH_SHORT).show();
                } else {
                   try {
                       double totalharga = Double.parseDouble(intent.getStringExtra("harga")) * Double.parseDouble(quantity.getText().toString());
                       ModelCart cart = new ModelCart(-1, intent.getStringExtra("nama"), Double.parseDouble(intent.getStringExtra("harga")), Integer.parseInt(quantity.getText().toString()), totalharga);
                       boolean b = databaseHelper.AddToCart(cart);
                       if (b == true){
                           Toast.makeText(com.example.project.AddToCart.this, "Status : Berhasil Menyimpan!", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(com.example.project.AddToCart.this, "Status : Gagal Menyimpan" , Toast.LENGTH_SHORT).show();
                       }

                       Intent intent1 = new Intent(com.example.project.AddToCart.this, total_cart.class);
                       intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       finish();
                   } catch (Exception e) {
                       Toast.makeText(com.example.project.AddToCart.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });




    }
}
