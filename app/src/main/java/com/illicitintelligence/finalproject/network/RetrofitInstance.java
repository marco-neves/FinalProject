package com.illicitintelligence.finalproject.network;

import android.util.Log;

import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;
import com.illicitintelligence.finalproject.util.Constants;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private final String BASE_URL = Constants.BASE_URL;
    private GitHubService service;
    private OkHttpClient okHttpClient;
    private long cacheSize = 10 * 1024 * 1024;
    private Cache cache;

    public RetrofitInstance(File cacheDirectory) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message ->{}); //Log.d("TAG_X", message));

        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        cache =  new Cache(cacheDirectory, cacheSize);

        okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .addInterceptor(logging)
                .build();

        service = createService( getInstance() );
    }

    private Retrofit getInstance() {

        return new Retrofit
                .Builder()
                .baseUrl( BASE_URL )
                .addConverterFactory( GsonConverterFactory.create() )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .client(okHttpClient)
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

    public Observable<List<RepoResult>> getPrivateRepos(String token){
        return service.getPrivateRepos(token);
    }
}
