package com.illicitintelligence.finalproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder>{
    private List<CommitsResult> commits;
    private Context appContext;

    public CommitAdapter(List<CommitsResult> commits) {
        this.commits = commits;
    }

    @NonNull
    @Override
    public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        appContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.commit_item_layout, parent, false);

        return new CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
        holder.commitTitleTextView.setText(commits.get(position).getCommit().getMessage());
        holder.commitDateTextView.setText(commits.get(position).getCommit().getCommitter().getDate() + " hours ago");

        try{
            holder.commitAuthorTextView.setText(commits.get(position).getCommitter().getLogin());
            //commits.get(position).getAuthor().getLogin()

        }catch(Exception e){
            holder.commitAuthorTextView.setText("User");
            Logger.logIt("onBindViewHolder: "+e.getMessage());
        }

        try{
            Glide.with(appContext)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(commits.get(position).getCommitter().getAvatarUrl())
                    .into(holder.commitAvatarImageView);
        }catch (Exception e){
            Glide.with(appContext)
                    .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                    .load(Constants.DEFAULT_ICON)
                    .into(holder.commitAvatarImageView);
            Logger.logIt("onBindViewHolder: "+e.getMessage());
        }

        //compareDates(commits.get(position).getCommit().getCommitter().getDate());


    }

    private void compareDates(String commitTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");

        Date currentDate = new Date();

        String currentDateString = format.format(currentDate);



        String currentDateTime[] = currentDateString.split("T");

        Logger.logIt("compareDates: "+format.format(currentDate)+ ", "+commitTime);


        try {
            Date commitDate = new Date();
            currentDate = format.parse(format.format(currentDate));
            Logger.logIt("compareDates2: "+ currentDate);
            long diff = currentDate.getTime() - commitDate.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Logger.logIt("compareDates: "+diffDays+ " days and "+diffHours+" hours ago");

        } catch (Exception e) {
            e.printStackTrace();
        }
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
