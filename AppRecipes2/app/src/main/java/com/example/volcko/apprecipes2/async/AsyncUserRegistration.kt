package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.volcko.apprecipes2.inteface.GetUserService
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.mapJson.UserRegJson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class AsyncUserRegistration (var c: Context, private var username: String, private var pass: String, private var email: String) : AsyncTask<Void, Void, Boolean>(){

    private lateinit var pd: ProgressDialog
    var msq: String = "null"


    fun doRegistration(): Boolean{
        var userData: UserRegJson = UserRegJson(username, pass, email)
        val service = RetrofitClinetInstance.retrofitInstance?.create(GetUserService::class.java)
        val call = service?.getRegistration(userData)
        var status: Boolean = true
        var finish: Boolean = false

        call?.enqueue(object : Callback<UserRegJson> {
            override fun onResponse(call: Call<UserRegJson>, response: Response<UserRegJson>) {
                if (response.body()?.username == username) {
                    msq = "Username " + username + " is already used."
                    status = false
                } else if (response.body()?.email == email) {
                    msq = "Email " + email + " is already used."
                    status = false
                }

                finish = true
            }

            override fun onFailure(call: Call<UserRegJson>, t: Throwable) {
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
        pd.setTitle("Registration")
        pd.setMessage("Loading..Please wait")
        pd.show()
    }

    // perform background downloading of data
    override fun doInBackground(vararg voids: Void): Boolean {
        return doRegistration()
    }

    // when background finishes, dismiss dialog
    override fun onPostExecute(registration: Boolean) {
        super.onPostExecute(registration)

        pd.dismiss()
        if (registration == false){
            if (!msq.equals("null"))
                Toast.makeText(c, msq, Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(c, "Registration failed!", Toast.LENGTH_SHORT).show()
        } else {
            NoLog_activity().setRegStatus(true)
            NoLog_activity().setRegDone(true)
            Toast.makeText(c, "Registration successful", Toast.LENGTH_SHORT).show()
        }
    }
}