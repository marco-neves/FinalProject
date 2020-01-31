package com.illicitintelligence.finalproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccessToken {

    @SerializedName( "user_id" )
    @Expose
    private String userId;

    @SerializedName( "token" )
    @Expose
    private String token;

    public String getUserId() {
        return userId;
    }

    public String getTokenType() {
        return token;
    }
}
