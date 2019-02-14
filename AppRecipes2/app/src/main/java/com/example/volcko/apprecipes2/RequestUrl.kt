package com.example.volcko.apprecipes2

class RequestUrl {
    private val url = "https://safe-falls-78094.herokuapp.com/"
    private val meal = "meal/"
    private val user = "user/"
    private val add = "add"
    private val update = "update"

    private val login = "login/"
    private val byId = "id/"
    private val category = "category/"
    private val byMeal = "meal/"
    private val byIngredient = "ing/"
    private val allCategories = "cat"

    fun getUrl(): String {
        return url
    }

    fun getMeal(): String {
        return meal
    }

    fun getUser(): String {
        return user
    }

    fun getAdd(): String {
        return add
    }

    fun getUpdate(): String {
        return update
    }

    fun getLogin(): String {
        return login
    }

    fun getById(): String {
        return byId
    }

    fun getCategory(): String {
        return category
    }

    fun getByMeal(): String {
        return byMeal
    }

    fun getByIngredient(): String {
        return byIngredient
    }

    fun getAllCategories(): String {
        return allCategories
    }
}