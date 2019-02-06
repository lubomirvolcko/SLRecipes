package com.example.volcko.apprecipes2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("register.php")
    Call<User> performRegistration(@Query("nickname") String Nickname, @Query("email") String Email, @Query("pass") String Password);

    @GET("login.php")
    Call<User>performUserLogin(@Query("nickname") String Nickname, @Query("pass") String Password);

}
