package com.illicitintelligence.finalproject.network;

import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.util.Util;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private final String BASE_URL = Util.BASE_URL;
    private GitHubService service;

    public RetrofitInstance() {
        service = createService( getInstance() );
    }

    private Retrofit getInstance() {

        return new Retrofit
                .Builder()
                .baseUrl( BASE_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build();
    }

    private GitHubService createService(Retrofit retrofit) {

        return retrofit.create( GitHubService.class );

    }

    public Observable<List<RepoResult>> getRepoInstance(String repos) {
        return service.getRepoService( repos );
    }

    public Observable<List<CommitsResult>> getCommitInstance(String userName, String repository) {
        return service.getCommitService( userName, repository );
    }
}
