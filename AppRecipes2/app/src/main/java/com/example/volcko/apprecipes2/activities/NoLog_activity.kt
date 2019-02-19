package com.example.volcko.apprecipes2.activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.apprecipes2.*
import com.example.volcko.fragmenty.*
import com.example.volcko.testhttpcon.UserLogin
import com.example.volcko.testhttpcon.UserRegistration
import kotlinx.android.synthetic.main.activity_log_activity.*
import kotlinx.android.synthetic.main.app_bar_log_activity.*

class NoLog_activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var context: Context
    var manager = supportFragmentManager
    val PREFS_NAME: String = "SL_recipe_data"

    var dialog : ProgressDialog? = null

    private var regStatus: Boolean = false
    private var regDone: Boolean = false

    fun setRegStatus(x: Boolean) {
        this.regStatus = x
    }

    fun setRegDone(x: Boolean) {
        this.regDone = x
    }

    fun getRegStatus(): Boolean {
        return this.regStatus
    }

    fun getRegDone(): Boolean {
        return this.regStatus
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_log_activity)
        setSupportActionBar(toolbar)
        context = this
        dialog = ProgressDialog(this)

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
        val txtToolbarMenu = findViewById<TextView>(R.id.txtToolbarMenu) // text view in toolbar for fragment name
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        val menuLogo = findViewById<ImageView>(R.id.menu_logo) //image view logo in menu

        val btnNavSearch = findViewById<Button>(R.id.btnNavSearch) //btn search in nav bar
        val btnNavFilter = findViewById<Button>(R.id.btnNavFilter) //btn filter in nav bar
        btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
        btnNavFilter.isClickable = false

        txtToolbarMenu.visibility = View.INVISIBLE

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

        // show keyboard
        fun showKeyboard(view: EditText) {
            if (view.requestFocus()){
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
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
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
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
            logUsername.text.clear()
            logPass.text.clear()
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
            regUsername.text.clear()
            regEmail.text.clear()
            regPass1.text.clear()
            regPass2.text.clear()
            if (termsAndConditions.isChecked)
                termsAndConditions.toggle()
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
                if (isNetworkAvailable(this)) {
                    val username: String = logUsername.text.toString()
                    val pass: String = logPass.text.toString()
                    UserLogin(this, username, pass).execute()
                } else
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegEnter.setOnClickListener {
            val regex = Regex(pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
            val matched = regex.containsMatchIn(input = regEmail.text)
            closeKeyboard()
            if (regUsername.length()==0 || regEmail.length()==0 || regPass1.length()==0 || regPass2.length()==0){
                Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(regUsername.length()<5){
                Toast.makeText(this, "Min length of Username is 5!", Toast.LENGTH_SHORT).show()
            }else if(regUsername.length()>15){
                Toast.makeText(this, "Max length of Username is 15!", Toast.LENGTH_SHORT).show()
            }else if(regPass1.length()<5){
                Toast.makeText(this, "Min length of password is 5!", Toast.LENGTH_SHORT).show()
            }else if(regEmail.length()<5){
                Toast.makeText(this, "Min length of email is 5!", Toast.LENGTH_SHORT).show()
            }else if(!matched){
                Toast.makeText(this, "Not valid email!", Toast.LENGTH_SHORT).show()
            }else{
                if (isNetworkAvailable(this)){
                    val pass1: String = regPass1.text.toString()
                    val pass2: String = regPass2.text.toString()
                    val username = regUsername.text.toString()
                    val email = regEmail.text.toString()

                    if (pass1 == pass2) {
                        if (!termsAndConditions.isChecked){
                            Toast.makeText(this, "Have to accept Terms & conditions", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        regPass1.text = null
                        regPass2.text = null
                        Toast.makeText(this, "Passwords are not equals!", Toast.LENGTH_SHORT).show()
                    }

                    UserRegistration(this, username, pass1, email).execute()
                    /*
                    if (getRegStatus()){
                        showHideView(registerView) //hide view register
                        showHideButton(btnLogin) //set visible button login
                        showHideButton(btnRegister) //set visible button register
                        regUsername.text.clear()
                        regEmail.text.clear()
                        regPass1.text.clear()
                        regPass2.text.clear()
                        if (termsAndConditions.isChecked)
                            termsAndConditions.toggle()
                    }
                    */
                } else
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()

            }
            //do staff
        }

        // action on button search in entry page
        btnSearchMain.setOnClickListener {
            if (txtMainSearch.text.isEmpty()) {
                Toast.makeText(this, "Have to write something!", Toast.LENGTH_SHORT).show()
            } else {
                if (isNetworkAvailable(this)) {
                    setNavBarSearch(toolbar) //set toolbar
                    showHideView(mainContent) //set main content to invisible
                    btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter, 0, 0)
                    btnNavFilter.isClickable = true
                    showFragmentSearch() // set fragment search to visible
                    closeKeyboard(btnSearchMain) // close keyboard
                    Toast.makeText(this, txtMainSearch.text, Toast.LENGTH_SHORT).show()
                    txtMainSearch.text = null
                } else
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        btnNavSearch.setOnClickListener {
            if (txtToolbarSearch.visibility == View.INVISIBLE){
                txtToolbarMenu.visibility = View.INVISIBLE
                txtToolbarMenu.text = ""
                txtToolbarSearch.visibility = View.VISIBLE
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter, 0, 0)
                btnNavFilter.isClickable = true
                txtToolbarSearch.requestFocus()
                showKeyboard(txtToolbarSearch)
            } else {
                if (isNetworkAvailable(this)){
                    showFragmentSearch() // set fragment search to visible
                    closeKeyboard(btnNavSearch) // close keyboard
                } else
                    Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        btnNavFilter.setOnClickListener {
            Toast.makeText(this, "SEARCH FILTER", Toast.LENGTH_SHORT).show()
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

    override fun finish() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage("Do you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id -> super.finish() }
            .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    override fun onBackPressed() {

        if (drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()

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
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Top Rated"

                //val intent = Intent(this, TopRated_activity::class.java)
                // To pass any data to next activity
                //intent.putExtra("keyIdentifier", value)
                // start your next activity
                //startActivity(intent)
            }
            // set action on click item Newest in menu
            R.id.nav_newest -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentNewest() //set fragment newest to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Newest"
            }
            // set action on click item Categories in menu
            R.id.nav_categories -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentCategories() //set fragment categories to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "Categories"
            }
            // set action on click item ABout Us in menu
            R.id.nav_aboutUs -> {
                if (mainContent.visibility == View.VISIBLE) {
                    mainContent.visibility = View.INVISIBLE
                }
                showFragmentAbutUs() //set fragment about us to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                txtToolbarMenu.visibility = View.VISIBLE
                txtToolbarMenu.text = "About us"
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //show fragment search
    fun showFragmentSearch() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentSearch()
        fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment top rated
    fun showFragmentTopRated() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentTopRated()
        fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment newest
    fun showFragmentNewest() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentNewest()
        fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment categories
    fun showFragmentCategories() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentCategories()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment about us
    fun showFragmentAbutUs() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentAboutUs()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
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

}
