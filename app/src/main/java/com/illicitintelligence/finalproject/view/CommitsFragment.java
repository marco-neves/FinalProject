package com.illicitintelligence.finalproject.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.adapter.CommitAdapter;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.util.Util;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class CommitsFragment extends Fragment {

    @BindView(R.id.recyclerView_commits)
    RecyclerView commitsRecyclerView;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RepoViewModel repoViewModel;
    private String repoTitle;

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
        repoTitle = argumentBundle.getString(Util.REPO_KEY);

        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel.class);
        getMyCommits(repoTitle);
    }

    private void setRV(List<CommitsResult> commitsResults) {
        CommitAdapter commitAdapter = new CommitAdapter(commitsResults);
        commitsRecyclerView.setAdapter(commitAdapter);
        commitsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

    }

    private void getMyCommits(String repoTitle) {
        compositeDisposable.add( repoViewModel.getMyCommits( repoTitle ).subscribe(
                new Consumer<List<CommitsResult>>() {
                    @Override
                    public void accept(List<CommitsResult> repoResults) throws Exception {
                        setRV(repoResults);

                        for (int i = 0; i < repoResults.size(); i++) {
                            Log.d( "TAG_E", "Commits: " + repoResults.get( i ).getCommit().getMessage() );
                        }


                    }
                }
                )
        );
    }

}
