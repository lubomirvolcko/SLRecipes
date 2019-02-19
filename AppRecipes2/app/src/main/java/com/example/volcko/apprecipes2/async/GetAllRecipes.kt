package com.example.volcko.apprecipes2.async

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.volcko.apprecipes2.GetUserService
import com.example.volcko.apprecipes2.R.id.recyclerView_main
import com.example.volcko.apprecipes2.RetrofitClinetInstance
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.adapter.MainAdapter
import com.example.volcko.apprecipes2.data.HomeFeed
import com.example.volcko.apprecipes2.mapJson.UserInfo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


@Suppress("DEPRECATION")
class GetAllRecipes (var c: Context?, val recyclerView: RecyclerView) : AsyncTask<Void, Void, Boolean>(){

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



    fun fetchJson(): Boolean {
        println("Attempting to Fetch JSON")

        val url = "https://safe-falls-78094.herokuapp.com/meal"

        val request = Request.Builder().url(url).build()

        val recyclerViewMain = recyclerView

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {


                val body = response.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                recyclerViewMain.adapter = MainAdapter(homeFeed)


            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("Failed to execute request")
            }
        })

        return true
    }

    /*
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
    */


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
        //return doLogin()
        return fetchJson()
    }

    // when background finishes, dismiss dialog and pass data to JSONParser
    override fun onPostExecute(login: Boolean) {
        super.onPostExecute(login)

        pd.dismiss()
        Toast.makeText(c, "DONE", Toast.LENGTH_SHORT).show()
        /*
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
        */
    }
}