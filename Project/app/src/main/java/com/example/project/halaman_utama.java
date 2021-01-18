package com.example.project;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class halaman_utama extends Fragment {
FloatingActionButton add;
ListView listView;
TextView textView;
DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_halaman_utama, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add = view.findViewById(R.id.btnAdd);
        listView = view.findViewById(R.id.listmakan);
        textView = view.findViewById(R.id.tx_header);
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        ViewList(databaseHelper);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), addmenu.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataModel dataModel = (DataModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DeleteUpdateMenu.class);
                intent.putExtra("id", String.valueOf(dataModel.getId()));
                intent.putExtra("nama", dataModel.getNama_makanan());
                intent.putExtra("harga", String.valueOf(dataModel.getHarga_makanan()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewList(databaseHelper);
    }

    private void ViewList(DatabaseHelper databaseHelper2) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), databaseHelper2.retrieveAll());
        listView.setAdapter(listViewAdapter);
    }

}
