package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EditCart extends Activity {
    Button increment,decrement,update, delete;
    TextView quantity;
    EditText nama,harga;
    int buy, newid;
    double totalHarga, hargaItem;
    ModelCart modelCart;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcart);
        setTitle("Add Item To Cart");

        increment = findViewById(R.id.btn_increment);
        decrement = findViewById(R.id.btn_decrement);
        update = findViewById(R.id.btn_updateItem);
        delete = findViewById(R.id.btn_deleteItem);
        quantity = findViewById(R.id.quantity);
        nama = findViewById(R.id.et_nama);
        harga = findViewById(R.id.et_harga);

        Intent intent = getIntent();
        newid = Integer.parseInt(intent.getStringExtra("id"));
        nama.setText(intent.getStringExtra("nama"));
        harga.setText(intent.getStringExtra("harga"));
        hargaItem = Double.parseDouble(intent.getStringExtra("harga"));
        quantity.setText(intent.getStringExtra("quantity"));
        buy = Integer.parseInt(quantity.getText().toString());

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
                if(buy == 1) {
                    Toast.makeText(EditCart.this, "Item Cant Below 1!", Toast.LENGTH_SHORT).show();
                } else {
                    buy--;
                    quantity.setText(String.valueOf(buy));
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelCart = new ModelCart(newid, nama.getText().toString(), Double.parseDouble(harga.getText().toString()), Integer.parseInt(quantity.getText().toString()), totalHarga);
                boolean b = databaseHelper.deletePerItem(modelCart);
                if(b) {
                    Toast.makeText(EditCart.this, "Data Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(EditCart.this, total_cart.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    Toast.makeText(EditCart.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalHarga = hargaItem * Integer.parseInt(quantity.getText().toString());
                modelCart = new ModelCart(newid, nama.getText().toString(), Double.parseDouble(harga.getText().toString()), Integer.parseInt(quantity.getText().toString()), totalHarga);
                boolean b = databaseHelper.updatePerItem(modelCart);
                if(b) {
                    Toast.makeText(EditCart.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(EditCart.this, total_cart.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    Toast.makeText(EditCart.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
