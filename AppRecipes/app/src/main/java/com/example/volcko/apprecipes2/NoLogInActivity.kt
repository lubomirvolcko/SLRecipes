package com.example.volcko.apprecipes2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.fragmenty.*
import android.widget.Toast
import java.io.*
import java.net.*


@Suppress("DEPRECATION")
class NoLogInActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var userID: Int = 0

    lateinit var context: Context
    private var tag: String = "NoLogInActivity"
    var manager = supportFragmentManager
    var login = false

    lateinit var prefConfig: PrefConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        context = this

        prefConfig = PrefConfig(this)


        val btnLogin = findViewById<Button>(R.id.btnLogin) //btn login - entry page
        val loginView = findViewById<View>(R.id.login_view) // view login
        val btnCloseLogin = findViewById<Button>(R.id.btnCloseLogin) //btn close in login view
        val btnLoginEnter = findViewById<Button>(R.id.btnLoginEnter) //btn registration in registration view

        val btnRegister = findViewById<Button>(R.id.btnRegister) //btn register - entry page
        val registerView = findViewById<View>(R.id.register_view) // view register
        val btnCloseRegister = findViewById<Button>(R.id.btnCloseRegister) //btn close in register view
        val btnRegEnter = findViewById<Button>(R.id.btnRegEnter) //btn registration in registration view

        val regNickname = findViewById<EditText>(R.id.txtNickname_reg) //txt nickname in registration
        val regPass1 = findViewById<EditText>(R.id.txtPassword1) //txt password1 in registration
        val regPass2 = findViewById<EditText>(R.id.txtPassword2) //txt password2 in registration
        val regEmail = findViewById<EditText>(R.id.txtEmail) //txt email in registration
        val termsAndConditions = findViewById<CheckBox>(R.id.termsAndConditions) //checkbox terms and condition in registration

        val logNickname = findViewById<EditText>(R.id.txtNickname) //txt nickname in login
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
            if (logNickname.length()==0 || logPass.length()==0){
                Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(logNickname.length()<5){
                Toast.makeText(this, "Min size of Nickname is 5!", Toast.LENGTH_SHORT).show()
            }else if(logPass.length()<5){
                Toast.makeText(this, "Min size of password is 5!", Toast.LENGTH_SHORT).show()
            }else{
                val nickname: String = logNickname.text.toString()
                val pass: String = logPass.text.toString()
                val reg = userLogin(nickname, pass, userID)
                if (reg.equals(getString(R.string.no_internet))){
                    Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()
                }else if (userID==0){
                    logNickname.text = null
                    logPass.text = null
                }else if(reg.equals("jes")){
                    logNickname.text = null
                    logPass.text = null
                    showHideView(loginView) //hide view login
                    showHideButton(btnLogin) //set visible button login
                    showHideButton(btnRegister) //set visible button register
                }
                else{
                    Toast.makeText(this, "do pice", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnRegEnter.setOnClickListener {
            if (regNickname.length()==0 || regEmail.length()==0 || regPass1.length()==0 || regPass2.length()==0){
                Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(regNickname.length()<5){
                Toast.makeText(this, "Min size of Nickname is 5!", Toast.LENGTH_SHORT).show()
            }else if(regPass1.length()<5){
                Toast.makeText(this, "Min size of password is 5!", Toast.LENGTH_SHORT).show()
            }else if(regEmail.length()<5){
                Toast.makeText(this, "Min size of email is 5!", Toast.LENGTH_SHORT).show()
            }else if(!regEmail.text.contains("@") || !regEmail.text.contains(".")){
                Toast.makeText(this, "Not valid email!", Toast.LENGTH_SHORT).show()
            }else{
                val nickname: String = regNickname.text.toString()
                val pass1: String = regPass1.text.toString()
                val pass2: String = regPass2.text.toString()
                val email: String = regEmail.text.toString()

                if (pass1 == pass2) {
                    if (termsAndConditions.isChecked){
                        val reg = userRegistration(nickname, email, pass1)
                        when(reg){
                            getString(R.string.reg_success) -> {
                                Toast.makeText(this, R.string.reg_success, Toast.LENGTH_SHORT).show()
                                regNickname.text = null
                                regPass1.text = null
                                regPass2.text = null
                                regEmail.text = null
                                termsAndConditions.isChecked=false
                                showHideView(registerView) //hide view register
                                showHideButton(btnLogin) //set visible button login
                                showHideButton(btnRegister) //set visible button register
                            }

                            getString(R.string.reg_fail)-> Toast.makeText(this, R.string.reg_fail, Toast.LENGTH_SHORT).show()
                            getString(R.string.no_internet) -> Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show()

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
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentTopRated() //set fragment top rated to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
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

    fun userRegistration(nickname: String, email: String, pass: String): String? {
        if (isNetworkAvailable(this)){
            if (AsyncTaskExampleReg(this, nickname, email, pass).execute().equals(getString(R.string.reg_fail))){
                return getString(R.string.reg_fail)
            }else{
                return getString(R.string.reg_success)
            }
        }else{
            return getString(R.string.no_internet)
        }
    }

    fun userLogin(nickname: String, pass: String, userID: Int): String? {
        if (isNetworkAvailable(this)){
            AsyncTaskExampleLog(this, nickname, pass).execute().get()

            return null

        }else{
            return getString(R.string.no_internet)

        }

        return null
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncTaskExampleReg(private var activity: NoLogInActivity?, var nickname: String, val email: String, val pass: String) : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): String {

            val reg_url = "http://recipessl.000webhostapp.com/register.php"
            try {
                val url: URL = URL(reg_url)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val OS: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(OS, "UTF-8"))
                val data: String = URLEncoder.encode("nickname","UTF-8") + "=" + URLEncoder.encode(nickname, "UTF-8") + "&" +
                        URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("pass","UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")
                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                OS.close()

                return "Registration Success!"
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return e.printStackTrace().toString()
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(activity?.context, "Registration success!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("StaticFieldLeak")
    class AsyncTaskExampleLog(private var activity: NoLogInActivity?, val nickname: String, val pass: String) : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): String? {

            val reg_url = "http://recipessl.000webhostapp.com/login.php"
            try {
                val url: URL = URL(reg_url)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.doOutput = true
                val OS: OutputStream = httpURLConnection.outputStream
                val bufferedWriter = BufferedWriter(OutputStreamWriter(OS, "UTF-8"))
                val data: String = URLEncoder.encode("nickname","UTF-8") + "=" + URLEncoder.encode(nickname, "UTF-8") + "&" +
                        URLEncoder.encode("pass","UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")
                bufferedWriter.write(data)
                bufferedWriter.flush()
                bufferedWriter.close()
                OS.close()

                val inputStream: InputStream = httpURLConnection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream, "iso-8859-1"))
                var response = ""
                val iterator = bufferedReader.lineSequence().iterator()
                while(iterator.hasNext()) {
                    var line = iterator.next()
                    response += line
                }
                bufferedReader.close()
                inputStream.close()
                httpURLConnection.disconnect()

                return response

            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return e.printStackTrace().toString()
            }


        }

        override fun onPostExecute(result: String) {
            if (activity!=null){
                if (result.equals("Error - login fail")){
                    Toast.makeText(activity?.context, "Login fail! Try again!", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity?.context, result, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity?.context, "Activity is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
