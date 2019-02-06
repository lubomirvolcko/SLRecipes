package com.example.volcko.apprecipes2;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("response")
    private String Response;

    @SerializedName("nickname")
    private String Nickname;

    public String getResponse() {
        return Response;
    }

    public String getNickname() {
        return Nickname;
    }
}
