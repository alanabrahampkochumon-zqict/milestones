package com.cs360.weighttracker.components;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cs360.weighttracker.models.DailyWeight;

import java.util.List;

public class WeightItemAdapter extends RecyclerView.Adapter<WeightItemViewHolder> {
    private final List<DailyWeight> dailyWeights;
    private final View.OnClickListener onClickListener;

    public WeightItemAdapter(List<DailyWeight> dailyWeights, View.OnClickListener onClickListener) {
        this.dailyWeights = dailyWeights;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public WeightItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new WeightItemViewHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightItemViewHolder holder, int position) {
        DailyWeight weight = dailyWeights.get(position);
        holder.bind(weight);
        holder.itemView.setTag(weight.getId());
//        holder.itemView.setOnClickListener();
    }

    @Override
    public int getItemCount() {
        return dailyWeights.size();
    }
}
