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
import com.example.volcko.apprecipes2.adapter.CategoryAdapter
import com.example.volcko.apprecipes2.mapJson.Category
import com.example.volcko.testhttpcon.AsyncMealCategories

class fragmentCategories: Fragment(){
    val TAG = "FragmentCategories"

    private var categories: List<Category>? = null

    fun setCategories(x: List<Category>) {
        this.categories = x
    }

    fun getCategories(): List<Category>? {
        return this.categories
    }


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
        val view: View = inflater!!.inflate(R.layout.fragment_categories, container, false)

        val categories = mutableListOf<Category>()
        for (i in 0..10) {
            categories.add(Category("Breakfast"))
        }
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)


        /*
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CategoryAdapter(categories)
        }
        */
        AsyncMealCategories(context, rv).execute()

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
