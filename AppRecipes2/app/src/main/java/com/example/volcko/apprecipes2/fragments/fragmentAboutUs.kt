package com.example.volcko.fragmenty

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.volcko.apprecipes2.R
import kotlinx.android.synthetic.main.fragment_about_us.*

class fragmentAboutUs: Fragment(){
    val TAG = "FragmentAboutUs"

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
        val view: View = inflater!!.inflate(R.layout.fragment_about_us, container, false)

        val btnTerms = view.findViewById<Button>(R.id.btnTerms)
        val btnPrivacy = view.findViewById<Button>(R.id.btnPrivacy)
        val btnContact = view.findViewById<Button>(R.id.btnContact)

        btnTerms.setOnClickListener {
            txtAboutUsMain.text = btnTerms.text
            txtAboutUs.text = getText(R.string.terms_and_conditions)
        }

        btnPrivacy.setOnClickListener {
            txtAboutUsMain.text = btnPrivacy.text
            txtAboutUs.text = getText(R.string.privacy_policy)
        }

        btnContact.setOnClickListener {
            txtAboutUsMain.text = btnContact.text
            txtAboutUs.text = getText(R.string.contact)
        }

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

    
}
