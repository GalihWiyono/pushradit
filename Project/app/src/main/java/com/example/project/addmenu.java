package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class addmenu extends Activity {
    EditText nama,harga;
    Button addtocart;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Menu Baru");
        setContentView(R.layout.activity_addmenu);
        nama = findViewById(R.id.et_nama);
        harga =  findViewById(R.id.et_harga);
        addtocart =  findViewById(R.id.btn_updateItem);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel dataModel = new DataModel(-1, nama.getText().toString(), Double.parseDouble(harga.getText().toString()));
                boolean b = databaseHelper.AddMenu(dataModel);
                if (b) {
                    Toast.makeText(addmenu.this, "Berhasil Menambahkan Menu!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(addmenu.this, halaman_utama.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    Toast.makeText(addmenu.this, "Gagal Menambahkan Menu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
