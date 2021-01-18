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

public class ListTotalAdapter extends ArrayAdapter<ModelCart> {

    public ListTotalAdapter(Context context, List<ModelCart> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.isilist, parent, false);
        }

        ModelCart currentPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.tf_Nama);
        textView1.setText(currentPosition.getNama());

        TextView textView2 = currentItemView.findViewById(R.id.tf_quantity);
        textView2.setText("@"+String.valueOf(currentPosition.getQuantity()));

        TextView textView3 = currentItemView.findViewById(R.id.tf_Harga);
        textView3.setText(String.valueOf(currentPosition.getTotalHarga()));

        return currentItemView;
    }
}
