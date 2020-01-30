package com.illicitintelligence.finalproject.viewmodel;

import android.app.Application;
import android.util.Log;

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

    public RepoViewModel(@NonNull Application application) {
        super( application );
    }

    public Observable<List<RepoResult>> getMyRepo(String userName) {
        //Log.d("TAG_X", "getMyRepo: " + userName);
        return retrofitInstance.getRepoInstance( userName )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

    }

    public Observable<List<CommitsResult>> getMyCommits(String userName, String repository) {
        return retrofitInstance.
                getCommitInstance( userName, repository )
                .subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() );

    }
}