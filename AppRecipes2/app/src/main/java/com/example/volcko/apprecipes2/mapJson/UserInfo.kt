package com.example.volcko.apprecipes2.mapJson

import com.google.gson.annotations.SerializedName

data class UserInfo(
    val message: String,
    @SerializedName("userData") var userData: List<UserDTO>
){}