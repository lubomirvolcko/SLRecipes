@file:Suppress("DEPRECATION")

package com.example.volcko.apprecipes2.inteface

import com.example.volcko.apprecipes2.mapJson.UserInfo
import com.example.volcko.apprecipes2.mapJson.UserRegJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface GetUserService {
    @GET("user/")
    fun getAllUsers() : Call<UserInfo>

    @GET("user/login/{username}/{password}")
    fun getLogin(@Path("username") username: String,@Path("password") password: String) : Call<UserInfo>

    @POST("user/add")
    fun getRegistration(@Body mUserRegJson: UserRegJson): Call<UserRegJson>

}