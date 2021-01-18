package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class total_cart extends Fragment {
    ListView listView;
    TextView total,textView,textView1;
    Button btncheckout;

    DatabaseHelper databaseHelper;
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_total);
        total = view.findViewById(R.id.tf_totalHarga);
        btncheckout = view.findViewById(R.id.btnCheckout);
        textView = view.findViewById(R.id.textView2);
        textView1 = view.findViewById(R.id.textView4);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        ViewList(databaseHelper);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String tanggal = simpleDateFormat.format(date);
        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
                String history = databaseHelper.retrieveCustomCart();
                HistoryModel historyModel = new HistoryModel(-1,history,Double.parseDouble(total.getText().toString()),tanggal);
                boolean addhistory = databaseHelper.Addhistory(historyModel);
                if(addhistory){
                    Toast.makeText(getContext(), "History Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed To Added History", Toast.LENGTH_SHORT).show();
                }
                boolean b = databaseHelper.deleteData();
                if(b) {
                    Toast.makeText(getContext(), "Terima Kasih", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Transacation Failed!", Toast.LENGTH_SHORT).show();
                }
                ViewList(databaseHelper);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelCart modelCart = (ModelCart) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), EditCart.class);
                intent.putExtra("id", String.valueOf(modelCart.getId()));
                intent.putExtra("nama", modelCart.getNama());
                intent.putExtra("harga", String.valueOf(modelCart.getHarga()));
                intent.putExtra("quantity", String.valueOf(modelCart.getQuantity()));
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewList(databaseHelper);
        int finalharga = (int) databaseHelper.totalHarga();
        total.setText(String.valueOf(finalharga));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_total_cart, container, false);

    }
    private void ViewList(DatabaseHelper databaseHelper2) {
        ListTotalAdapter listViewAdapter = new ListTotalAdapter(getContext(), databaseHelper2.retriveData());
        listView.setAdapter(listViewAdapter);
    }
}