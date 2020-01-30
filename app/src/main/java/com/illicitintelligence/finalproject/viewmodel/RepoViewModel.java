package com.illicitintelligence.finalproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.network.RetrofitInstance;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepoViewModel extends AndroidViewModel {

    private RetrofitInstance retrofitInstance = new RetrofitInstance();
    private String userName = "Germl";

    public RepoViewModel(@NonNull Application application) {
        super( application );
    }

    public Observable<List<RepoResult>> getMyRepo(String userName) {
        return retrofitInstance.getRepoInstance( userName ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() );
    }

    public Observable<List<CommitsResult>> getMyCommits(String repository) {
        //User name hardcoded as Global variable for testing puproses.
        return retrofitInstance.getCommitInstance( userName, repository ).subscribeOn( Schedulers.io() ).observeOn( AndroidSchedulers.mainThread() );
    }
}
