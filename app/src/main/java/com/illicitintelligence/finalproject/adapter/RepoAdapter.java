package com.illicitintelligence.finalproject.adapter;

import android.content.Context;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.model.RepoResult;

import org.w3c.dom.Text;

import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder>{
    private List<RepoResult> repoResults;
    private RepoDelegate repoDelegate;
    private Context context;

    public RepoAdapter(List<RepoResult> repoResults, RepoDelegate repoDelegate) {
        this.repoResults = repoResults;
        this.repoDelegate = repoDelegate;
    }

    public interface RepoDelegate{
        void clickRepo(String repo);
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item_layout, parent, false);

        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.repoLanguageTextView.setText(repoResults.get(position).getLanguage());
        holder.repoURLTextView.setText(repoResults.get(position).getHtmlUrl());

        String dateTime = repoResults.get(position).getUpdatedAt();
        String date[] = dateTime.split("T");
        String dateFormat[] = date[0].split("-");
        holder.repoDateTextView.setText(dateFormat[1] + "/" + dateFormat[2] + "/" + dateFormat[0]);

        String title = repoResults.get(position).getName();
//        if(title.length() > 19)
//            holder.repoTitleTextView.setTextSize(16f);


        holder.repoTitleTextView.setText(title);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repoDelegate.clickRepo(repoResults.get(position).getName());
            }
        });

        Animation transition = AnimationUtils.loadAnimation(context, R.anim.transition_animation);
        holder.itemView.startAnimation(transition);

    }

    @Override
    public int getItemCount() {
        return repoResults.size();
    }

    class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView repoTitleTextView;
        TextView repoLanguageTextView;
        TextView repoURLTextView;
        TextView repoDateTextView;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            repoTitleTextView = itemView.findViewById(R.id.repo_title_textview);
            repoLanguageTextView = itemView.findViewById(R.id.repo_laguage_textview);
            repoURLTextView = itemView.findViewById(R.id.repo_url_textview);
            repoDateTextView = itemView.findViewById(R.id.repo_date_textview);
        }
    }
}
