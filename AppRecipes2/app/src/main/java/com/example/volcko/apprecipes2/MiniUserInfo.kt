package com.example.volcko.apprecipes2

data class MiniUserInfo(
    val message: String,
    val userData: Data
)

data class Data(
    val idUser: Int,
    val username: String,
    val email: String,
    val password: String
)