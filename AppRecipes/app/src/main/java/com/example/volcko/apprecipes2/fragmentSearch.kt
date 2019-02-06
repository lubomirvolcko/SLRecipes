package com.example.volcko.fragmenty

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volcko.apprecipes2.*
import org.json.JSONException
import org.json.JSONObject

class fragmentSearch: Fragment(){
    val TAG = "FragmentSearch"
    //val context: Context = this

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

        //AsyncTaskFindRecipe(this, txtMainSearch.text.toString()).execute()

        return inflater!!.inflate(R.layout.fragment_search, container, false)
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

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
/*
    @SuppressLint("StaticFieldLeak")
    class AsyncTaskFindRecipe(val m: fragmentSearch, val str: String) : AsyncTask<String, String, String>() {

        val dialog = ProgressDialog(m.context)

        override fun onPreExecute() {
            dialog.setMessage("Searching...")
            dialog.setTitle("Please wait")
            dialog.show()
        }

        override fun doInBackground(vararg p0: String?): String? {

            if (m.isNetworkAvailable(m.context)){
                val stringRequest = object : StringRequest(
                    Request.Method.POST, m.URL_GET_RECIPES,
                    Response.Listener<String> { response ->
                        try {
                            val obj = JSONObject(response)
                            if (!obj.getBoolean("error")){
                                val array = obj.getJSONArray("recipes")

                                for (i in 0..array.length() -1){
                                    val objectUser = array.getJSONObject(i)

                                    val recipe = Recipe(
                                        objectUser.getInt("id"),
                                        objectUser.getString("meal"),
                                        objectUser.getString("category"),
                                        objectUser.getString("area"),
                                        objectUser.getString("instructions"),
                                        objectUser.getString("image"),
                                        objectUser.getString("tags"),
                                        objectUser.getString("ingredients"),
                                        objectUser.getString("measures"),
                                        objectUser.getString("video"),
                                        objectUser.getString("source")
                                    )

                                    m.mRecipe.add(recipe)
                                    val adapter = RecipesList(m, m.mRecipe)
                                    m.mRecipeListView.adapter = adapter

                                }
                                if (dialog.isShowing()) {


                                    dialog.dismiss()

                                }
                            }else{
                                if (dialog.isShowing()) {
                                    dialog.dismiss()
                                    if(obj.getString("message").equals("no matches!")){
                                        m.setSearchNoMatches()
                                    }
                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    object : Response.ErrorListener{
                        override fun onErrorResponse(volleyError: VolleyError) {
                            Toast.makeText(m.context, volleyError.message, Toast.LENGTH_SHORT).show()
                        }
                    }){

                    @Throws(AuthFailureError::class)
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params.put("recipename", str)
                        return params
                    }
                }

                val requestQueue = Volley.newRequestQueue(m.context)
                requestQueue.add<String>(stringRequest)
                VolleySingleton.instance?.addToRequestQueue(stringRequest)

                return "done"

            }else{
                return "No Internet Connection!"
            }

        }

        override fun onPostExecute(result: String) {
        }
    }

    /*
    fun loadRecipes(){
        val
        AsyncTaskFindRecipe()
    }
*/
    @SuppressLint("StaticFieldLeak")
    class AsyncTaskFindRecipe(val str: String) : AsyncTask<String, String, String>() {

        val dialog = ProgressDialog(m.context)

        override fun onPreExecute() {
            dialog.setMessage("Logging")
            dialog.setTitle("Please wait")
            dialog.show()
        }

        override fun doInBackground(vararg p0: String?): String? {

            if (m.isNetworkAvailable(m.context)){
                val stringRequest = object : StringRequest(
                    Request.Method.POST, m.URL_GET_USERS,
                    Response.Listener<String> { response ->
                        try {
                            val obj = JSONObject(response)
                            if (!obj.getBoolean("error")){
                                val array = obj.getJSONArray("user")

                                for (i in 0..array.length() -1){
                                    val objectUser = array.getJSONObject(i)

                                    val user = User(
                                        objectUser.getInt("id"),
                                        objectUser.getString("username"),
                                        objectUser.getString("password"),
                                        objectUser.getString("email")
                                    )

                                    m.userData.add(user)

                                }
                                if (dialog.isShowing()) {
                                    dialog.dismiss()
                                    m.userID=m.userData[0].id
                                    if (obj.getString("message").equals("Successful!")) {
                                        m.setLogView()
                                    }
                                    Toast.makeText(m.context, m.userData[0].username + ", " + m.userID, Toast.LENGTH_SHORT).show()

                                }
                            }else{
                                if (dialog.isShowing()) {
                                    dialog.dismiss()
                                    if(obj.getString("message").equals("no matches!")){
                                        m.clearPass()
                                    }
                                    Toast.makeText(m.context, obj.getString("message"), Toast.LENGTH_SHORT).show()
                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    object : Response.ErrorListener{
                        override fun onErrorResponse(volleyError: VolleyError) {
                            Toast.makeText(m.context, volleyError.message, Toast.LENGTH_SHORT).show()
                        }
                    }){

                    @Throws(AuthFailureError::class)
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params.put("recipename", str)
                        return params
                    }
                }

                val requestQueue = Volley.newRequestQueue(m.context)
                requestQueue.add<String>(stringRequest)
                VolleySingleton.instance?.addToRequestQueue(stringRequest)

                return "done"

            }else{
                return "No Internet Connection!"
            }

        }

        override fun onPostExecute(result: String) {
        }
    }
    */
}
