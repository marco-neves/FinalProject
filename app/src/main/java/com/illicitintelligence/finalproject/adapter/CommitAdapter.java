package com.illicitintelligence.finalproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.util.Constants;
import com.illicitintelligence.finalproject.util.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder>{
    private List<CommitsResult> commits;
    private Context context;

    public CommitAdapter(List<CommitsResult> commits) {
        this.commits = commits;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commit_item_layout, parent, false);

        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        String message = commits.get(position).getCommit().getMessage();

        if(message.length() > 26)
            holder.commitTitleTextView.setTextSize(12f);
        if(message.length() > 19)
            holder.commitTitleTextView.setTextSize(16f);

        holder.commitTitleTextView.setText(message);


        try{
            holder.commitAuthorTextView.setText(commits.get(position).getAuthor().getLogin());
            //commits.get(position).getAuthor().getLogin()

        }catch(Exception e){
            holder.commitAuthorTextView.setText("User");
            Logger.logIt("onBindViewHolder: "+e.getMessage());
        }

        try{
            Glide.with(context)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(commits.get(position).getAuthor().getAvatarUrl())
                    .into(holder.commitAvatarImageView);
        }catch (Exception e){
            Glide.with(context)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(Constants.DEFAULT_ICON)
                    .into(holder.commitAvatarImageView);
            Logger.logIt("onBindViewHolder: "+e.getMessage());
        }

        holder.commitDateTextView.setText(compareDates(commits.get(position).getCommit().getCommitter().getDate()) + " hours ago");

        Animation transition = AnimationUtils.loadAnimation(context, R.anim.transition_animation);
        holder.itemView.startAnimation(transition);
    }

    private int compareDates(String commitTimeFull){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");

        Date currentDateObj = new Date();
        String currentDateString = format.format(currentDateObj);

        String commitDateTime[] = commitTimeFull.split("T");
        String currentDateTime[] = currentDateString.split("T");

        String commitDate[] = commitDateTime[0].split("-");
        String currentDate[] = currentDateTime[0].split("-");

        String commitTime[] = commitDateTime[1].split(":");
        String currentTime[] = currentDateTime[1].split(":");

        int diffYear = (Integer.parseInt(currentDate[0]) - Integer.parseInt(commitDate[0]));
        int diffMonth = (Integer.parseInt(currentDate[1]) - Integer.parseInt(commitDate[1]));
        int diffDay = (Integer.parseInt(currentDate[2]) - Integer.parseInt(commitDate[2]));

        int diffHour = (Integer.parseInt(currentTime[0]) - Integer.parseInt(commitTime[0]));

        int diffTotal = (diffYear*8760) + (diffMonth*730) + (diffDay*24) +diffHour;

        Log.d("TAG_X", "compareDates: "+diffTotal);

        return diffTotal;
    }

    @Override
    public int getItemCount() {
        return commits.size();
    }

    class CommitViewHolder extends RecyclerView.ViewHolder{
        private TextView commitTitleTextView;
        private TextView commitAuthorTextView;
        private TextView commitDateTextView;
        private ImageView commitAvatarImageView;

        public CommitViewHolder(@NonNull View itemView) {
            super(itemView);
            commitTitleTextView = itemView.findViewById(R.id.commits_title_textview);
            commitAuthorTextView = itemView.findViewById(R.id.commits_author_textview);
            commitDateTextView = itemView.findViewById(R.id.commits_date_textview);
            commitAvatarImageView = itemView.findViewById(R.id.commits_avatar_imageview);
        }

    }

}
