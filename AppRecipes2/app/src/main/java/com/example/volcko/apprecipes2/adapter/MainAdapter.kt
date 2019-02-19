package com.example.volcko.apprecipes2.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.data.HomeFeed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_view.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    val recipeNames = listOf("recipeone", "recipe two", "recipe three")

    //number of items
    override fun getItemCount(): Int {
        return homeFeed.recipes.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.recipe_view, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val recipeName = recipeNames.get(position)
        val recipe = homeFeed.recipes.get(position)
        holder?.view?.recipe_name.text = recipe.meal
        holder?.view?.recipe_num_review.text = recipe.id
        holder?.view?.recipe_label.text = recipe.category

        val width = 360
        val height = 175

        val recipe_img = holder?.view?.recipe_img
        Picasso.with(holder?.view.context)
            .load(recipe.image)
            .resize(width, height)
            //.centerInside()
            .onlyScaleDown()
            .into(recipe_img)
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}