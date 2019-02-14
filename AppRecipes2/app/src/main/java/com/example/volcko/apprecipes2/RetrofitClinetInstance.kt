package com.example.volcko.apprecipes2

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClinetInstance {
    private var retrofit: Retrofit? = null
    private val BASE_URL = "https://safe-falls-78094.herokuapp.com/"

    // create retrofit instance, only if it has not been created yet
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }

            return retrofit
        }
}