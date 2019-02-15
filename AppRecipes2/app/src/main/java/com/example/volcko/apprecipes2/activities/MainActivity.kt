package com.example.volcko.apprecipes2.activities


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.volcko.apprecipes2.data.User

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    lateinit var activityIntent: Intent

    lateinit var mPrefs: SharedPreferences
    val PREFS_NAME: String = "SL_recipe_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val idUser: String? = getString(R.string.idUser)
        mPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        if (getPreferencesData() == null) {
            activityIntent = Intent(this, NoLog_activity::class.java)
            //activityIntent = Intent(this, Log_activity::class.java)

        } else {
            var arr: Array<User?> = arrayOf(getPreferencesData())
            activityIntent = Intent(this, Log_activity::class.java)
            //activityIntent = Intent(this, NoLog_activity::class.java)
            activityIntent.putExtra("userData", arr)
        }

        startActivity(activityIntent)
        finish()
    }

    fun getPreferencesData(): User? {
        val sp: SharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (sp.contains("idUser")) {
            var idUser: String = sp.getString("idUser", "not found")
            var username: String = sp.getString("username", "not found")
            var password: String = sp.getString("pass", "not found")
            var email: String = sp.getString("eamil", "not found")

            var userData: User =
                User(idUser, username, password, email)

            return userData

        } else {
            return null
        }
    }
}
