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

public class ListHistoryAdapter extends ArrayAdapter<HistoryModel> {

    public ListHistoryAdapter(Context context, List<HistoryModel> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if(currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.isihistory, parent, false);
        }

        HistoryModel currentPosition = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.tf_AllItem);
        textView1.setText(currentPosition.getAllitem());

        TextView textView3 = currentItemView.findViewById(R.id.tf_TotalHarga);
        textView3.setText(String.valueOf(currentPosition.getTotalharga()));

        return currentItemView;
    }
}
