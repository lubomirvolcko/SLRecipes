package com.example.volcko.fragmenty

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.adapter.RecipeDetailAdapter
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.testhttpcon.AsyncMealGetAll
import com.example.volcko.testhttpcon.AsyncMealGetRecipeDetail
import java.util.ArrayList

class fragmentRecipeDetail: Fragment(){
    val TAG = "FragmentFavorites"

    private var fav: Boolean = true
    private var recipes = ArrayList<Recipes>()
    private lateinit var adapter: RecipeDetailAdapter

    override fun onAttach(context: Context?) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        val view: View = inflater!!.inflate(R.layout.fragment_recipe_detail, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewRecipeDetail)

        rv.layoutManager = LinearLayoutManager(context)

        adapter = RecipeDetailAdapter(context, fav)


        rv.adapter = adapter

        val id = arguments?.getString("id")
        val act = arguments?.getString("act")

        AsyncMealGetRecipeDetail(context, true, id!!, adapter, act!!).execute()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG, "onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach")
        super.onDetach()
    }


}
