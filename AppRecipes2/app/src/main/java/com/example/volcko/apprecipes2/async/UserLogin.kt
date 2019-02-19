package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.volcko.apprecipes2.GetUserService
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.mapJson.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class UserLogin (var c: Context, private var username: String, private var pass: String) : AsyncTask<Void, Void, Boolean>(){

    private lateinit var pd: ProgressDialog
    lateinit var activityIntent: Intent

    fun doLogin(): Boolean{
        val service = RetrofitClinetInstance.retrofitInstance?.create(GetUserService::class.java)
        val call = service?.getLogin(username, pass)
        var status: Boolean = true
        var finish: Boolean = false


        activityIntent = Intent(c, Log_activity::class.java)

        call?.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val body = response?.body()
                val usersList = body?.userData
                val msg = body?.message

                activityIntent.putExtra("idUser", usersList?.get(0)?.idUser.toString())
                activityIntent.putExtra("username", usersList?.get(0)?.username.toString())
                activityIntent.putExtra("pass", usersList?.get(0)?.pass.toString())
                activityIntent.putExtra("email", usersList?.get(0)?.email.toString())
                c.startActivity(activityIntent)

                finish = true

            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                status = false
                finish = true
            }
        })

        do {
            print("WAIT...")
        } while (finish == false)

        return status
    }


    // show dialog while downloading data
    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Login in")
        pd.setMessage("Loading..Please wait")
        pd.show()
    }

    // perform background downloading of data
    override fun doInBackground(vararg voids: Void): Boolean {
        return doLogin()
    }

    // when background finishes, dismiss dialog
    override fun onPostExecute(login: Boolean) {
        super.onPostExecute(login)

        pd.dismiss()
        if (login == false){
            Toast.makeText(c, "Login failed...invalid username or password!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(c, "Login successful", Toast.LENGTH_SHORT).show()
        }
    }
}