package com.illicitintelligence.finalproject.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder>{

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class CommitViewHolder extends RecyclerView.ViewHolder{

        public CommitViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
