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
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.apprecipes2.*
import com.example.volcko.fragmenty.*
import kotlinx.android.synthetic.main.activity_log_activity.*
import kotlinx.android.synthetic.main.app_bar_log_activity.*

class NoLog_activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var context: Context
    var manager = supportFragmentManager
    val PREFS_NAME: String = "SL_recipe_data"

    private lateinit var txtToolbarMenu: TextView

    lateinit var tagFragmnet: String

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

    fun setContext(x: Context) {
        this.context = x
    }

    fun getContext(): Context {
        return this.context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_log_activity)
        setSupportActionBar(toolbar)
        setContext(this)
        dialog = ProgressDialog(this)


        val toolbarNoLog = findViewById<Toolbar>(R.id.toolbar)
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
        this.txtToolbarMenu = findViewById<TextView>(R.id.txtToolbarMenu) // text view in toolbar for fragment name
        val mainContent = findViewById<View>(R.id.content_main) //view content_main
        val menuLogo = findViewById<ImageView>(R.id.menu_logo) //image view logo in menu

        val btnNavSearch = findViewById<Button>(R.id.btnNavSearch) //btn search in nav bar
        val btnNavFilter = findViewById<Button>(R.id.btnNavFilter) //btn filter in nav bar
        btnNavFilter.visibility = View.INVISIBLE
        btnNavSearch.visibility = View.INVISIBLE
        txtToolbarMenu.visibility = View.INVISIBLE
        txtToolbarSearch.visibility = View.INVISIBLE

        btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
        btnNavFilter.isClickable = false




        // set visibility of view
        fun showHideView(view: View) {
            view.visibility = if (view.visibility == View.VISIBLE) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }
        }

        showFragmentNoLog()
        showHideView(searchBar) //set visibility view searchBar

        /*
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
            toolbar.setBackgroundColor(Color.parseColor("#BF000000")) //set background color to dark
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
        */

        // close keyboard
        fun closeKeyboard() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(btnNavSearch.getWindowToken(), 0)
        }

        //toggle navigation menu
        val toggle = object: ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ){
            override fun onDrawerStateChanged(newState: Int) {
                closeKeyboard()
                toolbarNoLog.setBackgroundColor(Color.parseColor("#00000000")) //set background color to transparent
                super.onDrawerStateChanged(newState)
            }
        }

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    } //bundle end

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

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
        //val mainContent = findViewById<View>(R.id.content_main) //view content_main
        when (item.itemId) {
            // set action on click item Top Rated in menu
            R.id.nav_topRated -> {
                showFragmentTopRated() //set fragment top rated to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                //txtToolbarMenu.visibility = View.VISIBLE
                //txtToolbarMenu.text = "Top Rated"
            }
            // set action on click item Newest in menu
            R.id.nav_newest -> {
                showFragmentNewest() //set fragment newest to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                //txtToolbarMenu.visibility = View.VISIBLE
                //txtToolbarMenu.text = "Newest"
            }
            // set action on click item Categories in menu
            R.id.nav_categories -> {
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                val bundle: Bundle = Bundle()
                bundle.putString("act", "noLog")
                showFragmentCategories(bundle) //set fragment categories to visible
                txtToolbarSearch.visibility = View.INVISIBLE
                //txtToolbarMenu.visibility = View.VISIBLE
                //txtToolbarMenu.text = "Categories"
            }
            // set action on click item About Us in menu
            R.id.nav_aboutUs -> {
                showFragmentAbutUs() //set fragment about us to visible
                setNavBarSearch(toolbar)
                setSearchedTextToNull()
                btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
                btnNavFilter.isClickable = false
                txtToolbarSearch.visibility = View.INVISIBLE
                //txtToolbarMenu.visibility = View.VISIBLE
                //txtToolbarMenu.text = "About us"
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    //show fragment search
    fun showFragmentNoLog() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentNoLog()
        tagFragmnet = "FragmentSearch"
        //fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment, tagFragmnet)
        transaction.addToBackStack(fragmentNoLog().TAG)
        transaction.commit()
    }



    //show fragment top rated
    fun showFragmentTopRated() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentTopRated()
        val bundle: Bundle = Bundle()
        bundle.putString("act", "noLog")
        fragment.arguments = bundle
        tagFragmnet = "FragmentSearch"
        //fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment, tagFragmnet)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment newest
    fun showFragmentNewest() {
        val transaction = manager.beginTransaction()
        val fragment = fragmentNewest()
        val bundle: Bundle = Bundle()
        bundle.putString("act", "noLog")
        fragment.arguments = bundle
        fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //show fragment categories
    fun showFragmentCategories(bundle: Bundle) {
        val transaction = manager.beginTransaction()
        val fragment = fragmentCategories()
        fragment.arguments = bundle
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

}
