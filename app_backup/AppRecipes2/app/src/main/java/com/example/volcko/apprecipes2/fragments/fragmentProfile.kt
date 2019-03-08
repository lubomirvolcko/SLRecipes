package com.example.volcko.fragmenty

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.volcko.apprecipes2.R
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.adapter.LastViewedAdapter
import com.example.volcko.apprecipes2.data.User
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.testhttpcon.AsyncMealGetLastViewed
import kotlinx.android.synthetic.main.category_view_black.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


class fragmentProfile: Fragment(){

    val TAG = "FragmentProfile"
    val PREFS_NAME: String = "SL_recipe_data"
    private lateinit var mPrefs: SharedPreferences
    private var lastViewed = ArrayList<Recipes>()

    private lateinit var adapter: LastViewedAdapter

    lateinit var activityIntent: Intent

    fun getPreferencesData(): User? {
        val sp: SharedPreferences = context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sp.contains("idUser")) {
            var idUser: String = sp.getString("idUser", "not found")
            var username: String = sp.getString("username", "not found")
            var password: String = sp.getString("pass", "not found")
            var email: String = sp.getString("email", "not found")
            var lastViewed1: String = sp.getString("lastViewed1", "not found")
            var lastViewed2: String = sp.getString("lastViewed2", "not found")
            var lastViewed3: String = sp.getString("lastViewed3", "not found")

            var userData: User = User(idUser, username, password, email, lastViewed1, lastViewed2, lastViewed3)

            return userData

        } else {
            return null
        }

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
        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)

        val btnLogout = view.findViewById<Button>(R.id.profileLogOut)
        val txtUserName = view.findViewById<TextView>(R.id.txtProfileUsername)

        view.yourRecipes.categoryName.text = "Your Recipes 0"
        view.yourFavRecipes.categoryName.text = "Favorites 0"

        view.yourRecipes.setOnClickListener {
            Toast.makeText(context, "You don't have any recipes", Toast.LENGTH_SHORT).show()
        }

        view.yourFavRecipes.setOnClickListener {
            Toast.makeText(context, "You don't have any favorites recipes", Toast.LENGTH_SHORT).show()
        }


        var arr: Array<User?> = arrayOf(getPreferencesData())
        txtUserName.text = arr.get(0)?.getUsername()



        val rv = view.findViewById<RecyclerView>(R.id.recyclerViewLastViewed)

        rv.layoutManager = LinearLayoutManager(context)

        adapter = LastViewedAdapter(context)
        rv.adapter = adapter


        val act = arguments!!.getString("act")

        AsyncMealGetLastViewed(context, true, act, adapter, lastViewed).execute()

        btnLogout.setOnClickListener {
            val settings = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            settings?.edit()?.clear()?.commit()

            activityIntent = Intent(context, NoLog_activity::class.java)
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(activityIntent)

            Toast.makeText(context, "Log Out", Toast.LENGTH_SHORT).show()
        }

        val btnFav = view.findViewById<Button>(R.id.btnFav) // btn favorite in recipe view

        //Favorite(context, view)

        /*
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
        */
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

    override fun onPause() {
        Log.d(TAG,"onPause")
        super.onPause()
    }
    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView")
        super.onDestroyView()
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
