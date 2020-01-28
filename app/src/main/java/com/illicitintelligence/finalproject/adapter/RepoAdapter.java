package com.illicitintelligence.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.model.RepoResult;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private List<RepoResult> repoResults;

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item_layout, parent, false);

        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        //holder.projectTitleTextview.setText(repoResults.get(position).);
    }

    @Override
    public int getItemCount() {
        return repoResults.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView projectTitleTextview;
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);

            projectTitleTextview = itemView.findViewById(R.id.project_title_textview);
        }
    }
}
