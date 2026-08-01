package com.cs360.weighttracker.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360.weighttracker.R;
import com.cs360.weighttracker.models.DailyWeight;
import com.cs360.weighttracker.utils.DateFormatter;

public class WeightItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView weightTextView;
    private final TextView dateTextView;
    private final ImageButton deleteWeightItemButton;

    public WeightItemViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.weight_item, parent, false));
        this.weightTextView = parent.findViewById(R.id.tvHistoryItemWeight);
        this.dateTextView = parent.findViewById(R.id.tvHistoryItemDate);
        this.deleteWeightItemButton = parent.findViewById(R.id.btnHistoryItemDelete);
    }

    public void bind(DailyWeight weight) { // TODO: BIND Callback
        dateTextView.setText(DateFormatter.fromMillis(weight.getDateTimeMillis()));
        weightTextView.setText(String.valueOf(weight.getUserWeight())); // TODO: Add string formatter
    }
}
