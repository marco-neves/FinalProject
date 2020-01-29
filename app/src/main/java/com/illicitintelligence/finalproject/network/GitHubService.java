package com.illicitintelligence.finalproject.network;

import com.illicitintelligence.finalproject.model.CommitsResult;
import com.illicitintelligence.finalproject.model.RepoResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("/users/{user}/repos")
    Observable<List<RepoResult>> getRepoService(
            @Path("user") String user
    );

    @GET("/repos/{userName}/{repository}/commits")
    Observable<List<CommitsResult>> getCommitService(
            @Path("userName") String userName,
            @Path ("repository") String repository);
}
