package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.adapter.LastViewedAdapter
import com.example.volcko.apprecipes2.inteface.GetMealService
import com.example.volcko.apprecipes2.mapJson.MealCategory
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.apprecipes2.mapJson.RecipesFeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AsyncMealGetLastViewed (
    var c: Context?,
    //var rv: RecyclerView,
    var fav: Boolean,
    val act: String,
    val adapter: LastViewedAdapter,
    lastViewedArr: ArrayList<Recipes>
) : AsyncTask<Void, Void, Boolean>(){
    private lateinit var recipes: List<Recipes>


    private lateinit var pd: ProgressDialog
    lateinit var categories: List<MealCategory?>
    var lastViewed = ArrayList<String>()



    fun showData(recipes: List<Recipes>) {
        //rv.layoutManager = LinearLayoutManager(c)
        val size: Int = recipes.size
        /*
        for (i in 0..size-1) {
            println("DATA: " + recipes?.get(i).meal)
        }
        */
        //rv.adapter = AllMealAdapter(recipes, c!!, fav)
        //rv.adapter = AllMealAdapter()
        //rv.adapter = AllMealAdapter()
        val arr = ArrayList<Recipes>()
        arr.addAll(recipes)
        adapter.setData(arr, act)
    }

    fun doCategories(): Boolean{
        val checkCall: Int = lastViewed.size
        var doCall: Int = 0

        var status: Boolean = true
        var finish: Boolean = false


        val service = RetrofitClinetInstance.retrofitInstance?.create(GetMealService::class.java)
        val call = service?.getLastViewedRecipes()

        println("DO CATEGORIES")
        call?.enqueue(object : Callback<RecipesFeed> {

            override fun onResponse(call: Call<RecipesFeed>, response: Response<RecipesFeed>) {

                val body = response.body()
                val rec = body?.recipes
                //val allCategories = body?.categories
                //val size = allCategories?.size
                /*
                categories = listOf(body)

                println("RESPONSE: ")
                val size: Int = body?.categories!!.size - 1
                for (i in 0..size) {
                    println("DATA: " + body?.categories?.get(i)?.category)
                }

                println("STATUS Response: " + status)


                */
                if (rec!=null) {
                    recipes = rec!!
                    showData(recipes)
                } else
                    Toast.makeText(c, "Failed to load Recipes", Toast.LENGTH_SHORT).show()

                pd.dismiss()

                finish = true

            }

            override fun onFailure(call: Call<RecipesFeed>, t: Throwable) {
                println("CATEGORIES FAIL")
                status = false
                println("STATUS Fail: "+ status)
                finish = true
            }
        })

        /*
        var x = 0
        do {
            x++
            print("WAIT ON BACKGROUND: " + x)
        } while (finish == false)
        */

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