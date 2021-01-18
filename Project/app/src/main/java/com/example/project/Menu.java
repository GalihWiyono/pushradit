package com.example.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Menu extends Fragment {
    Button btnadd, btnview, btnsearch;
    EditText fieldnama, fieldharga, fieldsearch;
    ListView listView;
    ArrayAdapter arrayAdapter;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu,null);
        return view;
    }
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.lv_makanan);
        fieldsearch = view.findViewById(R.id.et_Search);
        btnsearch = view.findViewById(R.id.btn_search);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        ViewList(databaseHelper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DataModel dataModel = (DataModel) parent.getItemAtPosition(position);
        Intent intent  = new Intent(getContext(), AddToCart.class);
        intent.putExtra("id", dataModel.getId());
        intent.putExtra("nama", dataModel.getNama_makanan());
        intent.putExtra("harga", String.valueOf(dataModel.getHarga_makanan()));
        startActivity(intent);
    }
});
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    List<DataModel> dataModels = databaseHelper.searchMenu(fieldsearch.getText().toString());
                    if (!dataModels.isEmpty()) {
                        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), dataModels);
                        listView.setAdapter(listViewAdapter);
                    } else {
                        Toast.makeText(getContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Syntax Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ViewList(DatabaseHelper databaseHelper2) {
        ListViewAdapter listViewAdapter = new ListViewAdapter(getContext(), databaseHelper2.retrieveAll());
        listView.setAdapter(listViewAdapter);
    }
}