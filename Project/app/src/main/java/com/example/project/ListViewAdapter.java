package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<DataModel> {

    public ListViewAdapter(Context context, List<DataModel> list) {
        super(context, 0 ,list);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.isimenu, parent, false);
        }
        DataModel currentNumberPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.tf_Nama);
        textView1.setText(currentNumberPosition.getNama_makanan());

        TextView textView2 = currentItemView.findViewById(R.id.tf_Harga);
        textView2.setText(String.valueOf(currentNumberPosition.getHarga_makanan()));

        return currentItemView;

    }
}
