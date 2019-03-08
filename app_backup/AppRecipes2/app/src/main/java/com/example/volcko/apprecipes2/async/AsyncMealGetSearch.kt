package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.adapter.SearchAdapter
import com.example.volcko.apprecipes2.inteface.GetMealService
import com.example.volcko.apprecipes2.mapJson.MealCategory
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.apprecipes2.mapJson.RecipesFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AsyncMealGetSearch (
    var c: Context?,
    //var rv: RecyclerView,
    var fav: Boolean,
    var search: String,

    val adapter: SearchAdapter,
    noMatches: TextView,
    searchBy: String?,
    val act: String
) : AsyncTask<Void, Void, Boolean>(){
    private lateinit var recipes: List<Recipes>


    private lateinit var pd: ProgressDialog
    lateinit var categories: List<MealCategory?>
    var lastViewed = ArrayList<String>()

    var matches = noMatches
    var searchByx = searchBy

    fun showData(recipes: List<Recipes>) {
        val arr = ArrayList<Recipes>()
        arr.addAll(recipes)
        adapter.setData(arr, matches, act!!)
    }

    fun doCategories(): Boolean{
        var status: Boolean = true

        val service = RetrofitClinetInstance.retrofitInstance?.create(GetMealService::class.java)
        var call: Call<RecipesFeed>?
        if (searchByx.equals("ing"))
            call = service?.getSearchIngredients(search)
        else
            call = service?.getSearchRecipes(search)

        println("SEARCHED TEXT IS : "+ search)

        println("DO CATEGORIES")
        call?.enqueue(object : Callback<RecipesFeed> {

            override fun onResponse(call: Call<RecipesFeed>, response: Response<RecipesFeed>) {

                val body = response.body()
                val rec = body?.recipes

                if (rec!=null) {
                    recipes = rec!!

                    showData(recipes)
                } else
                    Toast.makeText(c, "No Results", Toast.LENGTH_SHORT).show()

                pd.dismiss()


            }

            override fun onFailure(call: Call<RecipesFeed>, t: Throwable) {
                println("CATEGORIES FAIL")
                status = false
                println("STATUS Fail: "+ status)
                pd.dismiss()
            }
        })

        return status
    }

    // show dialog while downloading data
    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Loading Recipes")
        pd.setMessage("Please wait")
        pd.show()
    }

    // perform background downloading of data
    override fun doInBackground(vararg voids: Void): Boolean {
        return doCategories()
    }

    // when background finishes, dismiss dialog
    override fun onPostExecute(x: Boolean) {
        super.onPostExecute(x)
    }

}