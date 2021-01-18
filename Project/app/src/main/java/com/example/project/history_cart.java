package com.example.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class history_cart extends Fragment {
    EditText editText;
    TextView datepicker;
    ListView listView;
    DatePickerDialog.OnDateSetListener dateSetListener;
    DatabaseHelper databaseHelper;
    ImageView image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datepicker = (TextView) view.findViewById(R.id.textView);
        editText = (EditText) view.findViewById(R.id.editText);
        listView = view.findViewById(R.id.lv_history);
        image = view.findViewById(R.id.imageView);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryModel historyModel = (HistoryModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), HistoryDetail.class);
                intent.putExtra("allitem", historyModel.getAllitem());
                intent.putExtra("totalHarga", String.valueOf(historyModel.getTotalharga()));
                intent.putExtra("tanggal", historyModel.getTanggal());
                startActivity(intent);
            }
        });
        
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                List<HistoryModel> historyModels = databaseHelper.retrieveHistoryByDate(editText.getText().toString());
                if(!historyModels.isEmpty()) {
                    ListHistoryAdapter listViewAdapter = new ListHistoryAdapter(getContext(), historyModels);
                    listView.setAdapter(listViewAdapter);
                } else {
                    ListHistoryAdapter listViewAdapter = new ListHistoryAdapter(getContext(), historyModels);
                    listView.setAdapter(listViewAdapter);
                    Toast.makeText(getContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d("history_cart", "onDateSet: mm/dd/yy: "+ month +"/"+dayOfMonth + "/" + year);
                String date = dayOfMonth + "/" + month +"/" + year;
                editText.setText(date);
            }
        };

    }

    private void ViewList(DatabaseHelper databaseHelper2) {
        ListHistoryAdapter listViewAdapter = new ListHistoryAdapter(getContext(), databaseHelper2.retrieveHistory());
        listView.setAdapter(listViewAdapter);
    }
}