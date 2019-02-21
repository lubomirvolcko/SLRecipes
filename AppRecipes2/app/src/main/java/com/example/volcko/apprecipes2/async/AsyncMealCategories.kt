package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.adapter.CategoryAdapter
import com.example.volcko.apprecipes2.inteface.GetMealService
import com.example.volcko.apprecipes2.mapJson.Category
import com.example.volcko.apprecipes2.mapJson.MealCategory
import com.example.volcko.fragmenty.fragmentCategories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AsyncMealCategories (var c: Context?, var rv: RecyclerView) : AsyncTask<Void, Void, Boolean>(){

    private lateinit var pd: ProgressDialog
    lateinit var categories: List<MealCategory?>

    fun showData(categories: List<Category>) {
        rv.layoutManager = LinearLayoutManager(c)
        val size: Int = categories!!.size
        for (i in 0..size-1) {
            println("DATA: " + categories?.get(i).category)
        }
        rv.adapter = CategoryAdapter(categories, c!!)
    }

    fun doCategories(): Boolean{
        val service = RetrofitClinetInstance.retrofitInstance?.create(GetMealService::class.java)
        val call = service?.getAllCategories()
        var status: Boolean = true

        println("DO CATEGORIES")
        call?.enqueue(object : Callback<MealCategory> {

            override fun onResponse(call: Call<MealCategory>, response: Response<MealCategory>) {

                val body = response.body()
                //val allCategories = body?.categories
                //val size = allCategories?.size
                categories = listOf(body)

                if (body!=null) {
                    println("RESPONSE: ")
                    val size: Int = body?.categories!!.size
                    for (i in 0..size-1) {
                        println("DATA: " + body?.categories?.get(i)?.category)
                    }

                    println("STATUS Response: " + status)

                    showData(body.categories)
                } else
                    Toast.makeText(c, "Failed to load Categories", Toast.LENGTH_SHORT).show()

                pd.dismiss()

            }

            override fun onFailure(call: Call<MealCategory>, t: Throwable) {
                println("CATEGORIES FAIL")
                status = false
                println("STATUS Fail: "+ status)
            }
        })

        return status
    }

    // show dialog while downloading data
    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Loading Categories")
        pd.setMessage("Please wait")
        pd.show()
    }

    // perform background downloading of data
    override fun doInBackground(vararg voids: Void): Boolean {
        return doCategories()
    }

    // when background finishes, dismiss dialog
    override fun onPostExecute(categories: Boolean) {
        super.onPostExecute(categories)
    }
}