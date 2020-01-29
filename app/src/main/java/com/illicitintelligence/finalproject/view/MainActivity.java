package com.illicitintelligence.finalproject.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.viewmodel.RepoViewModel;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RepoViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of( this ).get( RepoViewModel.class );


        getRepositories();

        getMyCommits();

    }

    private void getMyCommits() {
        compositeDisposable.add( viewModel.getMyCommits( "HW3" ).subscribe(
                new Consumer<List<CommitsResult>>() {
                    @Override
                    public void accept(List<CommitsResult> repoResults) throws Exception {


                        for (int i = 0; i < repoResults.size(); i++) {
                            Log.d( TAG, "Commits: " + repoResults.get( i ).getCommit().getMessage() );
                        }


                    }
                }
                )
        );
    }

    private void getRepositories() {
        compositeDisposable.add( viewModel.getMyRepo( "Germl" ).subscribe(
                new Consumer<List<RepoResult>>() {
                    @Override
                    public void accept(List<RepoResult> repoResults) throws Exception {

                        for (int i = 0; i < repoResults.size(); i++) {
                            Log.d( TAG, "accept: " + repoResults.get( i ).getName() );
                        }

                    }
                }
                )
        );
    }

}
