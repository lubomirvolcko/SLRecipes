package com.example.volcko.apprecipes2.data

import java.io.Serializable

class User(private val idUser : String, private val username : String, private val password : String, private val email : String) : Serializable{

    fun getId(): String {
        return this.idUser
    }

    fun getUsername(): String {
        return this.username
    }

    fun getPassword(): String {
        return this.password
    }

    fun getEmail(): String {
        return this.email
    }

}