package com.example.volcko.apprecipes2.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.fragmenty.fragmentRecipeDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_detail_content.view.*
import kotlinx.android.synthetic.main.recipe_view.view.*
import kotlinx.android.synthetic.main.recipe_view_detail.view.*

class RecipeDetailAdapter(
    //val recipes: List<Recipes>
    val c: Context?,
    fav: Boolean
) : RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder>() {
    private var recipes = ArrayList<Recipes>()
    private lateinit var loadActivity: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_detail_content, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val recipe = recipes[position]
            holder.itemView.recipeViewDetail_name.text = recipe.meal
            holder.itemView.recipe_detail_num_review.text = recipe.review.toString()
            holder.itemView.recipe_detail_label.text = recipe.category
            holder.itemView.txtInstructions.visibility = View.INVISIBLE

            holder.itemView.btnInstructions_recipe_detail.setOnClickListener {
                holder.itemView.btnInstructions_recipe_detail.setBackgroundColor(Color.parseColor("#ac3e25"))
                holder.itemView.btnIngredients_recipe_detail.setBackgroundColor(Color.parseColor("#8B8383"))
                holder.itemView.imgIngredients.visibility = View.INVISIBLE
                holder.itemView.txtInstructions.visibility = View.VISIBLE
                holder.itemView.txtInstructions.text = recipe.instructions
            }

        holder.itemView.btnIngredients_recipe_detail.setOnClickListener {
            holder.itemView.btnIngredients_recipe_detail.setBackgroundColor(Color.parseColor("#ac3e25"))
            holder.itemView.btnInstructions_recipe_detail.setBackgroundColor(Color.parseColor("#8B8383"))
            holder.itemView.txtInstructions.visibility = View.INVISIBLE
            holder.itemView.imgIngredients.visibility = View.VISIBLE
        }



            val width = 360
            val height = 300
            val btnFav = holder.itemView.btnFavDetail

            val recipe_detail_img = holder.itemView.recipe_detail_img
            Picasso.with(holder.itemView.context)
                .load(recipe.image)
                .resize(width, height)
                .onlyScaleDown()
                .into(recipe_detail_img)

            btnFav.setOnClickListener {
                if (btnFav.tag.equals("noFav")){
                    btnFav.setBackgroundResource(R.drawable.ic_fav)
                    btnFav.tag = "fav"
                    Toast.makeText(c, "Added to Favorites", Toast.LENGTH_SHORT).show()
                } else {
                    btnFav.setBackgroundResource(R.drawable.ic_fav_empty)
                    btnFav.tag = "noFav"
                    Toast.makeText(c, "Remove from Favorites", Toast.LENGTH_SHORT).show()
                }
            }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recipeName: TextView = itemView.recipeViewDetail_name
    }

    fun setData(
        xy: ArrayList<Recipes>,
        act: String
    ) {
        recipes = xy
        loadActivity = act
        notifyDataSetChanged()
    }

}
