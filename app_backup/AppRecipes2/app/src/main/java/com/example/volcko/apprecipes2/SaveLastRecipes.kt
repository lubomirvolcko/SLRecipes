package com.example.volcko.apprecipes2

import android.content.Context
import android.content.SharedPreferences

class SaveLastRecipes(context: Context?) {
    val c = context

    private lateinit var mPrefs: SharedPreferences
    val PREFS_NAME: String = "SL_recipe_userRecipes"

    fun saveLast(id: String, meal: String, category: String, review: Double?, image: String?) {
        val saveRecipes: Int? = checkSavedRecipes()
        val sp: SharedPreferences = c!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var editor = sp.edit()

        if (saveRecipes == null) {
            editor.putString("countRecipes", saveRecipes.toString())
            editor.putString("idMeal1", id)
            editor.putString("meal1", meal)
            editor.putString("category1", category)
            editor.putString("image1", image)
            editor.putString("review1", review.toString())
            editor.apply()
        } else if (saveRecipes == 1){
            editor.putString("countRecipes", saveRecipes.plus(1).toString())
            editor.putString("idMeal2", id)
            editor.putString("meal2", meal)
            editor.putString("category2", category)
            editor.putString("image2", image)
            editor.putString("review2", review.toString())
            editor.apply()
        } else if (saveRecipes == 2){
            editor.putString("countRecipes", saveRecipes.plus(1).toString())
            editor.putString("idMeal3", id)
            editor.putString("meal3", meal)
            editor.putString("category3", category)
            editor.putString("image3", image)
            editor.putString("review3", review.toString())
            editor.apply()
        } else if (saveRecipes == 3){
            val id2 = sp.getString("idMeal1", "not found")
            val meal2 = sp.getString("idMeal1", "not found")
            val category2 = sp.getString("idMeal1", "not found")
            val image2 = sp.getString("idMeal1", "not found")
            val review2 = sp.getString("idMeal1", "not found")

            editor.putString("countRecipes", saveRecipes.toString())
            editor.putString("idMeal2", id2)
            editor.putString("meal2", meal2)
            editor.putString("category2", category2)
            editor.putString("image2", image2)
            editor.putString("review2", review2)

            val id3 = sp.getString("idMeal2", "not found")
            val meal3 = sp.getString("idMeal2", "not found")
            val category3 = sp.getString("idMeal2", "not found")
            val image3 = sp.getString("idMeal2", "not found")
            val review3 = sp.getString("idMeal2", "not found")

            editor.putString("idMeal3", id3)
            editor.putString("meal3", meal3)
            editor.putString("category3", category3)
            editor.putString("image3", image3)
            editor.putString("review3", review3)

            editor.putString("idMeal1", id)
            editor.putString("meal1", meal)
            editor.putString("category1", category)
            editor.putString("image1", image)
            editor.putString("review1", review.toString())
            editor.apply()
        }
    }

    private fun checkSavedRecipes(): Int? {
        var check: Int? = null
        val sp: SharedPreferences = c!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        if (sp.contains("countRecipes")) {
            var countRecipes: String? = sp.getString("countRecipes", "not found")

            if (!countRecipes.equals("null") && !countRecipes.equals("not found"))
                check = countRecipes!!.toInt()
        }

        return check
    }
}