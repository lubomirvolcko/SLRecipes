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

    @GET("meal/toprated")
    fun getTopRatedRecipes() : Call<RecipesFeed>

    @GET("meal/new")
    fun getNewRecipes() : Call<RecipesFeed>

    @GET("meal/lastviewed")
    fun getLastViewedRecipes() : Call<RecipesFeed>

    @GET("meal/found/{txt}")
    fun getSearchRecipes(@Path("txt") txt: String) : Call<RecipesFeed>

    @GET("meal/id/{txt}")
    fun getRecipeDetail(@Path("txt") txt: String) : Call<RecipesFeed>

    @GET("meal/ing/{txt}")
    fun getSearchIngredients(@Path("txt") txt: String) : Call<RecipesFeed>

    @GET("meal/category/{txt}")
    fun getRecipesByCategory(@Path("txt") txt: String) : Call<RecipesFeed>
}