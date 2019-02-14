package com.example.volcko.apprecipes2.data

class Recipe(private val id: String, private val name: String, private val category: String, private val area: String?,
             private val instructions: String, private val image: String?, private val tags: String?,
             private val ingredients: String, private val measures: String, private val video: String?,
             private val source: String?) {

    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getCategory(): String {
        return category
    }

    fun getArea(): String? {
        return area
    }

    fun getInstructions(): String {
        return instructions
    }

    fun getImage(): String? {
        return image
    }

    fun getTags(): String? {
        return tags
    }

    fun getIngredients(): String {
        return ingredients
    }

    fun getMeasures(): String {
        return measures
    }

    fun getVideo(): String? {
        return video
    }

    fun getSource(): String? {
        return source
    }

}