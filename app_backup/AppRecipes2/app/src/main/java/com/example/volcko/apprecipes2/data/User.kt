package com.example.volcko.apprecipes2.data

import java.io.Serializable

class User(private val idUser : String, private val username : String, private val password : String, private val email : String,
           private val lastViewed1 : String, private val lastViewed2 : String, private val lastViewed3 : String) : Serializable{

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

    fun getLastViewed1(): String {
        return this.lastViewed1
    }

    fun getLastViewed2(): String {
        return this.lastViewed2
    }
    fun getLastViewed3(): String {
        return this.lastViewed3
    }

}