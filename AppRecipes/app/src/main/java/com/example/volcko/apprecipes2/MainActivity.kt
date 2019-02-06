package com.example.volcko.apprecipes2


import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.fragmenty.*
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.volcko.apprecipes2.R.layout.fragment_search
import org.json.JSONException
import org.json.JSONObject


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var userID: Int = 0

    lateinit var context: Context
    var manager = supportFragmentManager

    private val URL_ROOT = "http://slrecipes.freevar.com/php/v1/index.php?op="
    //private val URL_ROOT = "192.168.0.188/v1/index.php?op="
    val URL_ADD_USER = URL_ROOT + "addUsers"
    val URL_GET_USERS = URL_ROOT + "getUsers"
    val URL_GET_RECIPES = URL_ROOT + "getRecipes"

    var userData = mutableListOf<User>()
    var recipeData = mutableListOf<Recipe>()
    var dialog : ProgressDialog? = null

    lateinit var mRecipeListView : ListView
    lateinit var mRecipe: MutableList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        context = this
        dialog = ProgressDialog(this)



        val noMatches = findViewById<TextView>(R.id.no_matches) //text no matches - fragment search

        val fragSearch = fragment_search
//        mRecipeListView.findViewById<ListView>(R.id.recipesView)
 //       mRecipe = mutableListOf<Recipe>()

        val btnLogin = findViewById<Button>(R.id.btnLogin) //btn login - entry page
        val loginView = findViewById<View>(R.id.login_view) // view login
        val btnCloseLogin = findViewById<Button>(R.id.btnCloseLogin) //btn close in login view
        val btnLoginEnter = findViewById<Button>(R.id.btnLoginEnter) //btn registration in registration view

        val btnRegister = findViewById<Button>(R.id.btnRegister) //btn register - entry page
        val registerView = findViewById<View>(R.id.register_view) // view register
        val btnCloseRegister = findViewById<Button>(R.id.btnCloseRegister) //btn close in register view
        val btnRegEnter = findViewById<Button>(R.id.btnRegEnter) //btn registration in registration view

        val regUsername = findViewById<EditText>(R.id.txtUsername_reg) //txt username in registration
        val regPass1 = findViewById<EditText>(R.id.txtPassword1) //txt password1 in registration
        val regPass2 = findViewById<EditText>(R.id.txtPassword2) //txt password2 in registration
        val regEmail = findViewById<EditText>(R.id.txtEmail) //txt email in registration
        val termsAndConditions = findViewById<CheckBox>(R.id.termsAndConditions) //checkbox terms and condition in registration

        val logUsername = findViewById<EditText>(R.id.txtUsername) //txt username in login
        val logPass = findViewById<EditText>(R.id.txtPassword) //txt password in login

        val btnSearchMain = findViewById<Button>(R.id.btnMainSearch) //btn search in entry page
        val searchBar = findViewById<View>(R.id.search_bar) // view search and filter
        val txtMainSearch = findViewById<EditText>(R.id.txtMainSearch) // edit text Search in entry page
        val txtToolbarSearch = findViewById<EditText>(R.id.txtToolbarSearch) // edit text Search in toolbar
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        val menuLogo = findViewById<ImageView>(R.id.menu_logo) //image view logo in menu

        val btnNavSearch = findViewById<Button>(R.id.btnNavSearch) //btn search in nav bar
        val btnNavFilter = findViewById<Button>(R.id.btnNavFilter) //btn filter in nav bar

        val recipeName = findViewById<TextView>(R.id.recipe_name) //text view of recipe name

        // set visibility of view
        fun showHideView(view: View) {
            view.visibility = if (view.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        // set visibility of buttons
        fun showHideButton(btn: Button) {
            btn.visibility = if (btn.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        // close keyboard
        fun closeKeyboard(btn: Button) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btn.getWindowToken(), 0)
        }

        // set visibility of buttons
        fun setNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
            toolbar.setBackgroundColor(Color.parseColor("#D9000000")) //set background color to dark
            showHideView(searchBar) //set visibility view searchBar
            val txtSearch = txtMainSearch.text
            txtToolbarSearch.text = txtSearch  //set text in toolbar edit text
        }

        // set buttons invisible
        fun setHideNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
            toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to transparent
            if (searchBar.visibility == View.VISIBLE)
                searchBar.visibility = View.INVISIBLE
            txtToolbarSearch.text = null  //set text in toolbar edit text
        }

        // close keyboard
        fun closeKeyboard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btnNavSearch.getWindowToken(), 0)
        }

        showHideView(loginView) // set visibility view login
        showHideView(registerView) // set visibility view register
        showHideView(searchBar) //set visibility view searchBar

        //toggle navigation menu
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        nav_view.setNavigationItemSelectedListener(this)

        // action on button login in entry page
        btnLogin.setOnClickListener {
            showHideView(loginView) //show view login
            showHideButton(btnLogin) //set invisible button login
            showHideButton(btnRegister) //set invisible button register
            closeKeyboard(btnLogin) // close keyboard
        }

        // action on button close in login view
        btnCloseLogin.setOnClickListener {
            showHideView(loginView) //hide view login
            showHideButton(btnLogin) //set visible button login
            showHideButton(btnRegister) //set visible button register
        }

        // action on button register in entry page
        btnRegister.setOnClickListener {
            showHideView(registerView) //show view register
            showHideButton(btnLogin) //set invisible button login
            showHideButton(btnRegister) //set invisible button register
            closeKeyboard(btnRegister) // close keyboard
        }

        // action on button close in register view
        btnCloseRegister.setOnClickListener {
            showHideView(registerView) //hide view register
            showHideButton(btnLogin) //set visible button login
            showHideButton(btnRegister) //set visible button register
        }

        btnLoginEnter.setOnClickListener {
            closeKeyboard()
            if (logUsername.length()==0 || logPass.length()==0){
                Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(logUsername.length()<5){
                Toast.makeText(this, "Min size of Username is 5!", Toast.LENGTH_SHORT).show()
            }else if(logPass.length()<5){
                Toast.makeText(this, "Min size of password is 5!", Toast.LENGTH_SHORT).show()
            }else{
                val username: String = logUsername.text.toString()
                val pass: String = logPass.text.toString()
                val reg = AsyncTaskLog(this ,username, pass)
                if (reg.execute().equals(getString(R.string.no_internet))){
                    Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegEnter.setOnClickListener {
            closeKeyboard()
            if (regUsername.length()==0 || regEmail.length()==0 || regPass1.length()==0 || regPass2.length()==0){
                Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(regUsername.length()<5){
                Toast.makeText(this, "Min size of Username is 5!", Toast.LENGTH_SHORT).show()
            }else if(regPass1.length()<5){
                Toast.makeText(this, "Min size of password is 5!", Toast.LENGTH_SHORT).show()
            }else if(regEmail.length()<5){
                Toast.makeText(this, "Min size of email is 5!", Toast.LENGTH_SHORT).show()
            }else if(!regEmail.text.contains("@") || !regEmail.text.contains(".")){
                Toast.makeText(this, "Not valid email!", Toast.LENGTH_SHORT).show()
            }else{
                val username: String = regUsername.text.toString()
                val pass1: String = regPass1.text.toString()
                val pass2: String = regPass2.text.toString()
                val email: String = regEmail.text.toString()

                val reg = AsyncTaskReg(this ,username, pass1, email)

                if (pass1 == pass2) {
                    if (termsAndConditions.isChecked){
                        if (reg.execute().equals(getString(R.string.no_internet))){
                            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Have to accept Terms & conditions", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    regPass1.text = null
                    regPass2.text = null
                    Toast.makeText(this, "Passwords are not equals!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // action on button search in entry page
        btnSearchMain.setOnClickListener {
            if (txtMainSearch.text.isEmpty()) {
                Toast.makeText(this, "Have to write something!", Toast.LENGTH_SHORT).show()
            } else {
                setNavBarSearch(toolbar) //set toolbar
                showHideView(mainContent) //set main content to invisible
                showFragmentSearch() // set fragment search to visible
                closeKeyboard(btnSearchMain) // close keyboard
                Toast.makeText(this, txtMainSearch.text, Toast.LENGTH_SHORT).show()
                txtMainSearch.text = null
            }
        }

        btnNavSearch.setOnClickListener {
            showFragmentSearch() // set fragment search to visible
            closeKeyboard(btnNavSearch) // close keyboard
        }

        // action on logo in menu
        menuLogo.setOnClickListener {
            if (mainContent.visibility == View.INVISIBLE)
                mainContent.visibility = View.VISIBLE

            searchBar.visibility = View.INVISIBLE
            toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to dark
            onBackPressed()
        }


    } //bundle end

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    // set visibility of buttons in navbar
    fun setNavBarSearch(toolbar: android.support.v7.widget.Toolbar) {
        toolbar.setBackgroundColor(Color.parseColor("#D9000000")) //set background color to dark
        val searchBar = findViewById<View>(R.id.search_bar) // view search and filter
        if (searchBar.visibility == View.INVISIBLE) {
            searchBar.visibility = View.VISIBLE
        }
    }

    //set text to null in toolbar searched edit text
    fun setSearchedTextToNull() {
        txtToolbarSearch.text = null  //set text in toolbar edit text
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        when (item.itemId) {
            // set action on click item Top Rated in menu
            R.id.nav_topRated -> {
                /*
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentTopRated() //set fragment top rated to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                */
                val intent = Intent(this, TopRated_activity::class.java)
                // To pass any data to next activity
                //intent.putExtra("keyIdentifier", value)
                // start your next activity
                startActivity(intent)
            }
            // set action on click item Top Rated in menu
            R.id.nav_newest -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentNewest() //set fragment newest to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
            }
            // set action on click item Top Rated in menu
            R.id.nav_categories -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentCategories() //set fragment categories to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
            }
            // set action on click item Top Rated in menu
            R.id.nav_aboutUs -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentAbutUs() //set fragment about us to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //show fragment search
    fun showFragmentSearch() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentSearch()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    //show fragment top rated
    fun showFragmentTopRated() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentTopRated()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    //show fragment newest
    fun showFragmentNewest() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentNewest()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    //show fragment categories
    fun showFragmentCategories() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentCategories()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    //show fragment about us
    fun showFragmentAbutUs() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentAboutUs()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun setLogView(){
        val btnRegister = findViewById<Button>(R.id.btnRegister) //btn register - entry page
        val btnLogin = findViewById<Button>(R.id.btnLogin) //btn login - entry page
        val loginView = findViewById<View>(R.id.login_view) // view login
        val logUsername = findViewById<EditText>(R.id.txtUsername) //txt username in login
        val logPass = findViewById<EditText>(R.id.txtPassword) //txt password in login

        logUsername.text = null
        logPass.text = null
        showHideView(loginView) //hide view login
        showHideButton(btnLogin) //set visible button login
        showHideButton(btnRegister) //set visible button register
    }

    fun setRegView(){
        val btnRegister = findViewById<Button>(R.id.btnRegister) //btn register - entry page
        val registerView = findViewById<View>(R.id.register_view) // view register

        val regUsername = findViewById<EditText>(R.id.txtUsername_reg) //txt username in registration
        val regPass1 = findViewById<EditText>(R.id.txtPassword1) //txt password1 in registration
        val regPass2 = findViewById<EditText>(R.id.txtPassword2) //txt password2 in registration
        val regEmail = findViewById<EditText>(R.id.txtEmail) //txt email in registration
        val termsAndConditions = findViewById<CheckBox>(R.id.termsAndConditions) //checkbox terms and condition in registration
        val btnLogin = findViewById<Button>(R.id.btnLogin) //btn login - entry page

        regUsername.text = null
        regPass1.text = null
        regPass2.text = null
        regEmail.text = null
        termsAndConditions.isChecked=false
        showHideView(registerView) //hide view register
        showHideButton(btnLogin) //set visible button login
        showHideButton(btnRegister) //set visible button register
    }

    fun clearPass(){
        val logPass = findViewById<EditText>(R.id.txtPassword) //txt password in login
        logPass.text = null
    }

    // set visibility of view
    fun showHideView(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    // set visibility of buttons
    fun showHideButton(btn: Button) {
        btn.visibility = if (btn.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    fun setSearchNoMatches(){
        val noMAtches = findViewById<TextView>(R.id.no_matches)
        noMAtches.visibility = View.VISIBLE
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncTaskReg(private var m: MainActivity, val username: String, val pass: String, val email : String) : AsyncTask<String, String, String>() {

        val dialog = ProgressDialog(m.context)

        override fun onPreExecute() {
            dialog.setMessage("Registration...")
            dialog.setTitle("Please wait")
            dialog.show()
        }

        override fun doInBackground(vararg p0: String?): String? {

            if (m.isNetworkAvailable(m.context)){

                val stringRequest = object : StringRequest(Request.Method.POST, m.URL_ADD_USER,
                    Response.Listener<String> { response ->
                        try {
                            val obj = JSONObject(response)
                            dialog.dismiss()
                            Toast.makeText(m.context, obj.getString("message"), Toast.LENGTH_SHORT).show()
                            if (obj.getString("message").equals("Registration successful!")){
                                m.setRegView()
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
                        params.put("username", username)
                        params.put("password", pass)
                        params.put("email", email)
                        return params
                    }
                }

                VolleySingleton.instance?.addToRequestQueue(stringRequest)

                val requestQueue = Volley.newRequestQueue(m.context)
                requestQueue.add<String>(stringRequest)
                VolleySingleton.instance?.addToRequestQueue(stringRequest)

                return "done"

            }else{
                return "No Internet Connection!"
            }

        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncTaskLog(private var m: MainActivity, val username: String, val pass: String) : AsyncTask<String, String, String>() {

        val dialog = ProgressDialog(m.context)

        override fun onPreExecute() {
            dialog.setMessage("Logging...")
            dialog.setTitle("Please wait")
            dialog.show()
        }

        override fun doInBackground(vararg p0: String?): String? {

            if (m.isNetworkAvailable(m.context)){
                val stringRequest = object : StringRequest(Request.Method.POST, m.URL_GET_USERS,
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
                                    if(obj.getString("message").equals("User don't exists!")){
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
                        params.put("username", username)
                        params.put("password", pass)
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
}
