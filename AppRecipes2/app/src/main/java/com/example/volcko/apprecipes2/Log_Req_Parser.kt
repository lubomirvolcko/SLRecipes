package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.widget.Toast
import com.example.volcko.apprecipes2.activities.Log_activity
import com.example.volcko.apprecipes2.data.User
import com.example.volcko.apprecipes2.MiniUserInfo
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

@Suppress("DEPRECATION")
class Log_Req_Parser(private var c: Context, private var jsonData: String, private var status: String) : AsyncTask<Void, Void, Boolean> (), Serializable{

    private lateinit var pd: ProgressDialog
    //private lateinit var user: User
    lateinit var activityIntent: Intent

    private var idUser = "x"
    private var username = "x"
    private var pass = "x"
    private var email = "x"
    private var message = ""

    fun setIdUser(x: String){
        this.idUser=x
    }

    fun setUsername(x: String){
        this.username=x
    }

    fun setPass(x: String){
        this.pass=x
    }

    fun setEmail(x: String){
        this.email=x
    }

    fun setMessage(x: String){
        this.message=x
    }

    fun getIdUser(): String{
        return idUser
    }

    fun getUsername(): String{
        return username
    }

    fun getPass(): String{
        return pass
    }

    fun getEmail(): String{
        return email
    }

    fun getMessage(): String{
        return message
    }



    /*
    class MrAdapter(private var c: Context, private var users: ArrayList<User>) : BaseAdapter() {

        override fun getCount(): Int {
            return users.size
        }

        override fun getItem(pos: Int): Any {
            return users[pos]
        }

        override fun getItemId(pos: Int): Long {
            return pos.toLong()
        }

        // inflate row_model.xml and return it

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var convertView: View? = view
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.recipe_view, viewGroup, false)
            }

            val categoryTxt = convertView!!.findViewById<TextView>(R.id.recipe_label) as TextView
            val nameTxt = convertView.findViewById<TextView>(R.id.recipe_name) as TextView
            val reviewTxt = convertView.findViewById<TextView>(R.id.recipe_num_review) as TextView

            val recipe = this.getItem(i) as User

            idTxt.text = recipe.getCategory()
            nameTxt.text = recipe.getName()
            reviewTxt.text = recipe.getId()

            convertView.setOnClickListener ({ Toast.makeText(c, recipe.getName(), Toast.LENGTH_SHORT).show() })

            return convertView
        }
    }

    */
    //parse JSON data
    private fun parse(): Boolean {
        var ret: Boolean
        try {
            var gson = Gson()
            var str = """{"message": "Login successful","user:data":{"idUser": "1","username": "sladmin","pass": "slpass","email": "admin@slrecipes.com"}}"""
            var data = gson.fromJson(str, MiniUserInfo::class.java)
            //val ja = JSONArray(jsonData)
            var jo: JSONObject

            //users.clear()
            //var user: User



            //for (i in 0 until ja.length()) {
            //    jo = ja.getJSONObject(i)
                setMessage(data.message)
                //val userData = jo.getJSONObject("userData")
                //val id = userData.getString("id")
                //val username = userData.getString("username")
                //val password = userData.getString("password")
                //val email = userData.getString("email")

                if (getMessage().equals("Login successful")){
                    //setIdUser(data.userData.idUser.toString())
                    //setUsername(data.userData.username)
                    //setPass(data.userData.password)
                    //setEmail(data.userData.email)
                    Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
                    ret = true
                } else {
                    //user = User("", "", "", "")
                    setMessage(data.message)
                    ret = false
                }


            //}

            return ret
        } catch (e: JSONException) {
            e.printStackTrace()
            return false
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Parse JSON")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        pd.dismiss()
        if (isParsed!!) {
            var arr: Array<User?> = arrayOf(
                User(
                    getIdUser(),
                    getUsername(),
                    getPass(),
                    getEmail()
                )
            )
            activityIntent = Intent(c, Log_activity::class.java)
            activityIntent.putExtra("userData", arr)
            c.startActivity(activityIntent)
        }else {
            //Toast.makeText(c, "Unable To Parse that data. ARE YOU SURE IT IS VALID JSON DATA? JsonException was raised. Check Log Output.", Toast.LENGTH_SHORT).show()
            //Toast.makeText(c, "THIS IS THE DATA WE WERE TRYING TO PARSE :  " + jsonData, Toast.LENGTH_LONG).show()
            //Toast.makeText(c, "Invalid username or password", Toast.LENGTH_SHORT).show()
            Toast.makeText(c, getMessage(), Toast.LENGTH_SHORT).show()
        }
    }

}