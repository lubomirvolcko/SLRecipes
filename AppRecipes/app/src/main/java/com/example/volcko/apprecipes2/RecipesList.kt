package com.example.volcko.apprecipes2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.net.URL

class RecipesList(private val context: Activity, internal  var recipe: List<Recipe>) :
    ArrayAdapter<Recipe>(context, R.layout.recipe_view, recipe) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.recipe_view, null, true)

        val recipeName = listViewItem.findViewById<TextView>(R.id.recipe_name)

        val myRecipe = recipe[position]
        recipeName.text = myRecipe.meal

        return listViewItem
    }
}