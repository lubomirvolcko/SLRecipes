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
import android.widget.Button
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.MainActivity
import com.example.volcko.apprecipes2.adapter.TopRatedAdapter
import com.example.volcko.testhttpcon.AsyncMealGetAll
import com.example.volcko.testhttpcon.AsyncMealGetNew

class fragmentNewest: Fragment(){
    val TAG = "FragmentNewst"
    private var fav: Boolean = true
    private lateinit var adapter: TopRatedAdapter

    fun setFav(x: Boolean) {
        this.fav = x
    }

    fun getFav(): Boolean {
        return fav
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
        val view: View = inflater!!.inflate(R.layout.fragment_newest, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewNewest)

        rv.layoutManager = LinearLayoutManager(context)

        adapter = TopRatedAdapter(context, fav)

        rv.adapter = adapter

        val act = arguments!!.getString("act")

        AsyncMealGetNew(context, true, act, adapter).execute()

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
