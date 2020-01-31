package com.illicitintelligence.finalproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.adapter.CommitAdapter;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.util.Constants;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class CommitsFragment extends Fragment {

    @BindView(R.id.recyclerView_commits)
    RecyclerView commitsRecyclerView;

    @BindView(R.id.commits_textView)
    TextView commitsTextView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RepoViewModel repoViewModel;
    private String repoTitle;
    private String currentAuthor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.commits_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        Bundle argumentBundle = getArguments();
        repoTitle = argumentBundle.getString(Constants.REPO_KEY);
        currentAuthor = argumentBundle.getString(Constants.AUTHOR_KEY);

        commitsTextView.setText(repoTitle +  " Commits");

        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        getMyCommits(currentAuthor, repoTitle);
    }

    private void setRV(List<CommitsResult> commitsResults) {
        CommitAdapter commitAdapter = new CommitAdapter(commitsResults);
        commitsRecyclerView.setAdapter(commitAdapter);
        commitsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }

    private void getMyCommits(String userName, String repoTitle) {
        compositeDisposable.add( repoViewModel.getMyCommits( userName, repoTitle ).subscribe(commitsResults -> {
           setRV(commitsResults);

           // TODO: Check >>24hr<< and if yes: reference sharedPrefs (caching)
            // if no, fetch the commits again and then cache

            //Toast.makeText(this.getContext(), "Commits Found", Toast.LENGTH_SHORT).show();
        }, throwable -> {
            Toast.makeText(this.getContext(), "No Commits Found for " + repoTitle, Toast.LENGTH_SHORT).show();
        }));
    }

}
