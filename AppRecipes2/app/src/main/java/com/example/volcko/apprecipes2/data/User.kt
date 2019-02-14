package com.example.volcko.apprecipes2.data

import java.io.Serializable

class User(private val idUser : String, private val username : String, private val password : String, private val email : String) : Serializable{

    fun getId(): String {
        return idUser
    }

    fun getUsername(): String {
        return username
    }

    fun getPassword(): String {
        return password
    }

    fun getEmail(): String {
        return email
    }

}