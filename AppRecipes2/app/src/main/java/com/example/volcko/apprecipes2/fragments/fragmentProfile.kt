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
import android.R.id.edit
import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.content.SharedPreferences
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.data.User


class fragmentProfile: Fragment(){

    val TAG = "FragmentProfile"
    val PREFS_NAME: String = "SL_recipe_data"
    private lateinit var mPrefs: SharedPreferences

    lateinit var activityIntent: Intent

    fun getPreferencesData(): User? {
        val sp: SharedPreferences = context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sp.contains("idUser")) {
            var idUser: String = sp.getString("idUser", "not found")
            var username: String = sp.getString("username", "not found")
            var password: String = sp.getString("pass", "not found")
            var email: String = sp.getString("email", "not found")

            var userData: User =
                User(idUser, username, password, email)

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


        var arr: Array<User?> = arrayOf(getPreferencesData())
        txtUserName.text = arr.get(0)?.getUsername()

        btnLogout.setOnClickListener {
            val settings = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            settings?.edit()?.clear()?.commit()

            activityIntent = Intent(context, NoLog_activity::class.java)
            startActivity(activityIntent)

            Toast.makeText(context, "Log Out", Toast.LENGTH_SHORT).show()
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
