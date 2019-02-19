package com.example.volcko.apprecipes2.retrofit

import com.example.volcko.apprecipes2.mapJson.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {
    @Suppress("DEPRECATION")
    @GET("user/login/{username}/{password}")
    fun getLogin(@Path("username") username: String, @Path("password") password: String) : Call<UserInfo>
}