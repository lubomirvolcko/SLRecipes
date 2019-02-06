package com.example.volcko.apprecipes2

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class PrefConfig(context: Context) {

    val context: Context = context
    val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE)

    fun writeLoginStatus(status: Boolean){
        val editor: SharedPreferences.Editor  = sharedPreferences.edit()
        editor.putBoolean(context.getString(R.string.pref_login_status), status)
        editor.commit()
    }

    fun readLoginStatus():Boolean{
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false)
    }

    fun writeNickname(nickname: String){
        val editor: SharedPreferences.Editor  = sharedPreferences.edit()
        editor.putString(context.getString(R.string.pref_user_name), nickname)
        editor.commit()
    }

    fun readNickname():String{
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "User")
    }

    fun displayToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}