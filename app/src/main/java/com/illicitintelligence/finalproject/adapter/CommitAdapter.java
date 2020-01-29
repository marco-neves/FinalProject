package com.illicitintelligence.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.model.Commit;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.view.CommitsFragment;

import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder>{
    private List<CommitsResult> commits;


    public CommitAdapter(List<CommitsResult> commits) {
        this.commits = commits;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commit_item_layout, parent, false);

        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        holder.commitTextView.setText(commits.get(position).getCommit().getMessage());
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    class CommitViewHolder extends RecyclerView.ViewHolder{
        private TextView commitTextView;

        public CommitViewHolder(@NonNull View itemView) {
            super(itemView);

            commitTextView = itemView.findViewById(R.id.commits_title_textview);
        }

    }

}
