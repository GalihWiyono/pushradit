package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class HistoryDetail extends Activity {
TextView tv1, tv2, tv3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historydetail);
        setTitle("History Detail");
        tv1 = findViewById(R.id.text1);
        tv2 = findViewById(R.id.txt2);
        tv3 = findViewById(R.id.text3);

        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("tanggal"));
        tv2.setText(intent.getStringExtra("allitem"));
        tv3.setText(intent.getStringExtra("totalHarga"));

    }
}
