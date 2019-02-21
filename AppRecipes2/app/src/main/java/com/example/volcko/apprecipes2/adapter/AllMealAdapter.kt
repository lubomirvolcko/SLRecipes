package com.example.volcko.apprecipes2.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recipe_view.view.*

class AllMealAdapter(
    //val recipes: List<Recipes>
    //val c: Context
    //val fav: Boolean
) : RecyclerView.Adapter<AllMealAdapter.ViewHolder>() {
    private var recipes = ArrayList<Recipes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.itemView.recipe_name.text = recipe.meal
        holder.itemView.recipe_num_review.text = recipe.id
        holder.itemView.recipe_label.text = recipe.category

        val width = 360
        val height = 175
        val btnFav = holder.itemView.btnFav

        val recipe_img = holder.itemView.recipe_img
        Picasso.with(holder.itemView.context)
            .load(recipe.image)
            .resize(width, height)
            .onlyScaleDown()
            .into(recipe_img)

        holder.itemView.setOnClickListener {
            //Toast.makeText(c, holder.recipeName.text.toString(), Toast.LENGTH_SHORT).show()
        }

        /*
        if (fav){
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
        } else
            btnFav.visibility = View.INVISIBLE
        */
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recipeName: TextView = itemView.recipe_name
    }

    fun setData(xy: ArrayList<Recipes>) {
        recipes = xy
        notifyDataSetChanged()
    }

}
