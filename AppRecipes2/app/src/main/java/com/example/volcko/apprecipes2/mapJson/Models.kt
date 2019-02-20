package com.example.volcko.apprecipes2.mapJson

class RecipesFeed(val recipes: List<Recipes>)

class Recipes(val id: String, val meal: String, val category: String, val area: String?,
              val instructions: String, val image: String?, val tags: String?,
              val ingredients: String, val measures: String, val video: String?,
              val source: String?)