@file:Suppress("DEPRECATION")

package com.example.volcko.apprecipes2.inteface

import com.example.volcko.apprecipes2.mapJson.MealCategory
import com.example.volcko.apprecipes2.mapJson.RecipesFeed
import com.example.volcko.apprecipes2.mapJson.UserInfo
import com.example.volcko.apprecipes2.mapJson.UserRegJson
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface GetMealService {
    @GET("meal/cat")
    fun getAllCategories() : Call<MealCategory>

    @GET("meal")
    fun getAllRecipes() : Call<RecipesFeed>


    @GET("meal/category/{category}")
    fun getLogin(@Path("category") category: String) : Call<UserInfo>

    @POST("user/add")
    fun getRegistration(@Body mUserRegJson: UserRegJson): Call<MealCategory>

}