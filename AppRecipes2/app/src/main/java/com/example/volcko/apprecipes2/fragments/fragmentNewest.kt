package com.example.volcko.fragmenty

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.MainActivity

class fragmentNewest: Fragment(){
    val TAG = "FragmentNewst"
    private var fav: Boolean = true

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

        val btnFav = view.findViewById<Button>(R.id.btnFav) // btn favorite in recipe view

        //Favorite(context, view)


        if (getFav()==false)
            btnFav.visibility = View.INVISIBLE
        else {
            btnFav.setOnClickListener {
                if (btnFav.tag.equals("noFav")){
                    btnFav.setBackgroundResource(R.drawable.ic_fav)
                    btnFav.tag = "fav"
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                } else {
                    btnFav.setBackgroundResource(R.drawable.ic_fav_empty)
                    btnFav.tag = "noFav"
                    Toast.makeText(context, "Remove from Favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
