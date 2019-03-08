package com.example.volcko.fragmenty

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.volcko.apprecipes2.R
import com.example.volcko.apprecipes2.activities.NoLog_activity
import com.example.volcko.apprecipes2.adapter.AllMealAdapter
import com.example.volcko.apprecipes2.mapJson.Recipes
import com.example.volcko.testhttpcon.AsyncUserLogin
import com.example.volcko.testhttpcon.AsyncUserRegistration
import kotlinx.android.synthetic.main.app_bar_no_log_activity.view.*
import java.util.*

class fragmentNoLog: Fragment(){
    val TAG = "FragmentNoLog"
    private var fav: Boolean = true
    private var recipes = ArrayList<Recipes>()
    private lateinit var adapter: AllMealAdapter
    lateinit var searchText: String

    //var manager = activity!!.supportFragmentManager
    /*
    private val adapter = AllMealAdapter(Collections.emptyList()


    fun setFav(x: Boolean) {
        this.fav = x
    }

    fun getFav(): Boolean {
        return fav
    }
    */

    // set visibility of view
    fun showHideView(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE)
            View.INVISIBLE
        else
            View.VISIBLE
    }

    // set visibility of buttons
    fun showHideButton(btn: Button) {
        btn.visibility = if (btn.visibility == View.VISIBLE)
            View.INVISIBLE
        else
            View.VISIBLE
    }


    // -////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // close keyboard
    fun closeKeyboard(view: View) {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    // show keyboard
    fun showKeyboard(view: EditText) {
        if (view.requestFocus()){
            val imm = activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
    // -////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // set visibility of buttons
    fun setNavBarSearch(toolbar: Toolbar, searchBar: View, txtMainSearch: EditText) {
        toolbar.setBackgroundColor(Color.parseColor("#BF000000")) //set background color to dark
        showHideView(searchBar) //set visibility view searchBar
        val txtSearch = txtMainSearch.text
        //txtToolbarSearch.text = txtSearch  //set text in toolbar edit text
    }

    /*
    //Create new fragment and transaction
    Fragment newFragment = new ExampleFragment();
    FragmentTransaction transaction = getFragmentManager().beginTransaction();

    //Replace whatever is in the fragment_container view with this fragment,
    and add the transaction to the back stack if needed
    transaction.replace(R.id.fragment_container, newFragment);
    transaction.addToBackStack(null);

    //Commit the transaction
    transaction.commit();
    * */

    //show fragment search
    fun showFragmentSearch(bundle: Bundle) {
        val transaction = fragmentManager!!.beginTransaction()
        val fragment = fragmentSearch()
        fragment.arguments = bundle
        fragment.setFav(false)
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


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

        val view: View = inflater!!.inflate(R.layout.fragment_no_log, container, false)

        val btnLogin = view.findViewById<Button>(R.id.btnLogin) //btn login - entry page
        val loginView = view.findViewById<View>(R.id.login_view) // view login
        val btnCloseLogin = view.findViewById<Button>(R.id.btnCloseLogin) //btn close in login view
        val btnLoginEnter = view.findViewById<Button>(R.id.btnLoginEnter) //btn registration in registration view

        val btnRegister = view.findViewById<Button>(R.id.btnRegister) //btn register - entry page
        val registerView = view.findViewById<View>(R.id.register_view) // view register
        val btnCloseRegister = view.findViewById<Button>(R.id.btnCloseRegister) //btn close in register view
        val btnRegEnter = view.findViewById<Button>(R.id.btnRegEnter) //btn registration in registration view

        val regUsername = view.findViewById<EditText>(R.id.txtUsername_reg) //txt username in registration
        val regPass1 = view.findViewById<EditText>(R.id.txtPassword1) //txt password1 in registration
        val regPass2 = view.findViewById<EditText>(R.id.txtPassword2) //txt password2 in registration
        val regEmail = view.findViewById<EditText>(R.id.txtEmail) //txt email in registration
        val termsAndConditions = view.findViewById<CheckBox>(R.id.termsAndConditions) //checkbox terms and condition in registration

        val logUsername = view.findViewById<EditText>(R.id.txtUsername) //txt username in login
        val logPass = view.findViewById<EditText>(R.id.txtPassword) //txt password in login

        val btnSearchMain = view.findViewById<Button>(R.id.btnMainSearch) //btn search in entry page
        val searchBar = view.findViewById<View>(R.id.search_bar) // view search and filter
        val txtMainSearch = view.findViewById<EditText>(R.id.txtMainSearch) // edit text Search in entry page
        val txtToolbarSearch = view.findViewById<EditText>(R.id.txtToolbarSearch) // edit text Search in toolbar
        val txtToolbarMenu = view.findViewById<TextView>(R.id.txtToolbarMenu) // text view in toolbar for fragment name
        val mainContent = view.findViewById<View>(R.id.content_main) //view content_main
        val menuLogo = view.findViewById<ImageView>(R.id.menu_logo) //image view logo in menu

        val btnNavSearch = view.findViewById<Button>(R.id.btnNavSearch) //btn search in nav bar
        val btnNavFilter = view.findViewById<Button>(R.id.btnNavFilter) //btn filter in nav bar
        //btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter_disable, 0, 0)
        //btnNavFilter.isClickable = false

        //txtToolbarMenu.visibility = View.INVISIBLE

        if (view.toolbar!=null)
            view.toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to dark

        if (txtToolbarMenu != null) {
            txtToolbarMenu.visibility = View.INVISIBLE
        }


        showHideView(loginView) // set visibility view login
        showHideView(registerView) // set visibility view register
        //showHideView(searchBar) //set visibility view searchBar



        // action on button login in entry page
        btnLogin.setOnClickListener {
            showHideView(loginView) //show view login
            showHideButton(btnLogin) //set invisible button login
            showHideButton(btnRegister) //set invisible button register
            closeKeyboard(view) // close keyboard
            txtMainSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }


        // action on button close in login view
        btnCloseLogin.setOnClickListener {
            closeKeyboard(view)
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
            closeKeyboard(view)
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
            closeKeyboard(btnLoginEnter)
            if (logUsername.length()==0 || logPass.length()==0){
                Toast.makeText(context, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(logUsername.length()<5){
                Toast.makeText(context, "Min size of Username is 5!", Toast.LENGTH_SHORT).show()
            }else if(logPass.length()<5){
                Toast.makeText(context, "Min size of password is 5!", Toast.LENGTH_SHORT).show()
            }else{
                if (NoLog_activity().isNetworkAvailable(context!!)) {
                    val username: String = logUsername.text.toString()
                    val pass: String = logPass.text.toString()
                    val c = context
                    AsyncUserLogin(c!!, username, pass).execute()
                } else
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegEnter.setOnClickListener {
            val regex = Regex(pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+")
            val matched = regex.containsMatchIn(input = regEmail.text)
            closeKeyboard(btnRegEnter)
            if (regUsername.length()==0 || regEmail.length()==0 || regPass1.length()==0 || regPass2.length()==0){
                Toast.makeText(context, "All fields must be filled in!", Toast.LENGTH_SHORT).show()
            }else if(regUsername.length()<5){
                Toast.makeText(context, "Min length of Username is 5!", Toast.LENGTH_SHORT).show()
            }else if(regUsername.length()>15){
                Toast.makeText(context, "Max length of Username is 15!", Toast.LENGTH_SHORT).show()
            }else if(regPass1.length()<5){
                Toast.makeText(context, "Min length of password is 5!", Toast.LENGTH_SHORT).show()
            }else if(regEmail.length()<5){
                Toast.makeText(context, "Min length of email is 5!", Toast.LENGTH_SHORT).show()
            }else if(!matched){
                Toast.makeText(context, "Not valid email!", Toast.LENGTH_SHORT).show()
            }else{
                if (NoLog_activity().isNetworkAvailable(context!!)){
                    val pass1: String = regPass1.text.toString()
                    val pass2: String = regPass2.text.toString()
                    val username = regUsername.text.toString()
                    val email = regEmail.text.toString()

                    if (pass1 == pass2) {
                        if (!termsAndConditions.isChecked){
                            Toast.makeText(context, "Have to accept Terms & conditions", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        regPass1.text = null
                        regPass2.text = null
                        Toast.makeText(context, "Passwords are not equals!", Toast.LENGTH_SHORT).show()
                    }

                    val c = context
                    AsyncUserRegistration(c!!, username, pass1, email).execute()
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
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()

            }
            //do staff
        }

        // action on button search in entry page
        btnSearchMain.setOnClickListener {
            if (txtMainSearch.text.isEmpty()) {
                Toast.makeText(context, "Have to write something!", Toast.LENGTH_SHORT).show()
            } else {
                if (NoLog_activity().isNetworkAvailable(context!!)) {
                    //setNavBarSearch(toolbar, searchBar, txtMainSearch) //set toolbar
                    //showHideView(mainContent) //set main content to invisible
                    //btnNavFilter.setCompoundDrawablesWithIntrinsicBounds( 0, R.drawable.ic_filter, 0, 0)
                    //btnNavFilter.isClickable = true
                    val bundle: Bundle = Bundle()
                    searchText = txtMainSearch.text.toString()
                    bundle.putString("search", searchText)

                    bundle.putString("act", "noLog")

                    showFragmentSearch(bundle) // set fragment search to visible
                    closeKeyboard(btnSearchMain) // close keyboard
                    Toast.makeText(context, txtMainSearch.text, Toast.LENGTH_SHORT).show()
                    txtMainSearch.text = null
                } else
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }


        /*
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
                if (NoLog_activity().isNetworkAvailable(context!!)){
                    val bundle: Bundle = Bundle()
                    bundle.putString("search", searchText)

                    showFragmentSearch(bundle) // set fragment search to visible
                    closeKeyboard(btnNavSearch) // close keyboard
                } else
                    Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }



        btnNavFilter.setOnClickListener {
            Toast.makeText(context, "SEARCH FILTER", Toast.LENGTH_SHORT).show()
        }



        // action on logo in menu
        menuLogo.setOnClickListener {
            if (mainContent.visibility == View.INVISIBLE)
                mainContent.visibility = View.VISIBLE

            searchBar.visibility = View.INVISIBLE
            toolbar.setBackgroundColor(Color.parseColor("#00000000")) //set background color to dark
            NoLog_activity().onBackPressed()
        }
        */
        return view

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
        Log.d(TAG, "onResume")
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

}
