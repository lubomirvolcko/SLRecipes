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


    private var message: String = "Login successful"
    private var idUser: String = "1"
    private var userName: String = "sladmin"
    private var password: String = "slpass"
    private var email: String = "admin@slrecipes.com"
    /*
    private lateinit var message: String
    private lateinit var idUser: String
    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var email: String
    */

    // getter
    fun getMessage(): String { return message }

    fun getIdUser(): String { return idUser }

    fun getUserName(): String { return userName }

    fun getPassword(): String { return password }

    fun getEmail(): String { return email }

    // setters
    fun setMessage(str: String) { this.message = str }

    fun setIdUser(str: String) { this.idUser = str }

    fun setUserName(str: String) { this.userName = str }

    fun setPassword(str: String) { this.password = str }

    fun setEmail(str: String) { this.email = str }

    fun doLogin(): Boolean{
        val service = RetrofitClinetInstance.retrofitInstance?.create(GetUserService::class.java)
        val call = service?.getLogin(username, pass)
        var status: Int = 0

        call?.enqueue(object : Callback<UserInfo> {
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                //Toast.makeText(c, "Err reading JSON", Toast.LENGTH_SHORT).show()
                status = 1
            }

            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val body = response?.body()
                val usersList = body?.userData
                val msg = body?.message
                //var size = usersList?.size

                setMessage(msg.toString())
                setIdUser(usersList?.get(0)?.idUser.toString())
                setUserName(usersList?.get(0)?.username.toString())
                setPassword(usersList?.get(0)?.pass.toString())
                setEmail(usersList?.get(0)?.email.toString())
            }

        })

        if (status == 1)
            return false
        else
            return true

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
        //return download()
        return doLogin()
    }

    // when background finishes, dismiss dialog and pass data to JSONParser
    override fun onPostExecute(login: Boolean) {
        super.onPostExecute(login)

        pd.dismiss()
        if (login == false){
            Toast.makeText(c, "Login failed", Toast.LENGTH_SHORT).show()
        } else {
            if (getMessage() == null)
                Toast.makeText(c, "MESSAGE PROBLEM", Toast.LENGTH_SHORT).show()
            else if (getMessage().equals("Invalid username or password"))
                Toast.makeText(c, getMessage(), Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(c, "Login successful", Toast.LENGTH_SHORT).show()

                activityIntent = Intent(c, Log_activity::class.java)
                activityIntent.putExtra("idUser", getIdUser())
                activityIntent.putExtra("username", getUserName())
                activityIntent.putExtra("pass", getPassword())
                activityIntent.putExtra("email", getEmail())
                c.startActivity(activityIntent)
            }
        }
    }
}