package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DeleteUpdateMenu extends Activity {
    EditText nama, harga;
    Button update, delete;
    DatabaseHelper databaseHelper = new DatabaseHelper(this);
    DataModel dataModel;
    int newid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Update/Delete Menu");
        setContentView(R.layout.activity_deletemenu);
        nama = findViewById(R.id.et_nama);
        harga = findViewById(R.id.et_harga);
        update = findViewById(R.id.btn_updateItem);
        delete = findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
         newid = Integer.parseInt(id);
        nama.setText(intent.getStringExtra("nama"));
        harga.setText(intent.getStringExtra("harga"));


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModel = new DataModel( newid , nama.getText().toString(), Double.parseDouble(harga.getText().toString()));
                boolean b = databaseHelper.updateMenu(dataModel);
                if(b) {
                    Toast.makeText(DeleteUpdateMenu.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(DeleteUpdateMenu.this, halaman_utama.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    Toast.makeText(DeleteUpdateMenu.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataModel = new DataModel( newid , nama.getText().toString(), Double.parseDouble(harga.getText().toString()));
                boolean b = databaseHelper.deleteMenu(dataModel);
                if(b) {
                    Toast.makeText(DeleteUpdateMenu.this, "Data Deleted!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(DeleteUpdateMenu.this, halaman_utama.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                } else {
                    Toast.makeText(DeleteUpdateMenu.this, "Delete Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
