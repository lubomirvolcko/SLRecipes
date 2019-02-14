package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

@Suppress("DEPRECATION")
class Log_Reg_Task(private var c: Context, private var jsonURL: String, private var status: String) : AsyncTask<Void, Void, String>(){

    private lateinit var pd: ProgressDialog

    // Connect to network via HTTPURLConnection

    private fun connect(jsonURL: String): Any {
        try {
            val url = URL(jsonURL)
            val con = url.openConnection() as HttpURLConnection

            // con props
            if (status.equals("log")){
                con.requestMethod = "GET"
                con.connectTimeout = 10000
                con.readTimeout = 10000
                con.doInput = true
            } else if (status.equals("reg")) {
                con.requestMethod = "POST"
                con.connectTimeout = 10000
                con.readTimeout = 10000
                con.doInput = true
            }


            return con

        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return "URL ERROR " + e.message

        } catch (e: IOException) {
            e.printStackTrace()
            return "CONNECT ERROR " + e.message
        }
    }

    // Download JSON data
    private fun download(): String {
        val connection = connect(jsonURL)
        if (connection.toString().startsWith("Error")) {
            return connection.toString()
        }
        //download
        try {
            val con = connection as HttpURLConnection
            //if response is HTTP OK
            if (con.responseCode == 200) {
                //g get input from stream
                val `is` = BufferedInputStream(con.inputStream)
                val br = BufferedReader(InputStreamReader(`is`))

                val jsonData = StringBuffer()
                var line: String?

                do {
                    line = br.readLine()

                    if (line == null) {break}

                    jsonData.append(line + "\n")

                } while (true)

                // close resources
                br.close()
                `is`.close()

                // return JSON
                return  jsonData.toString()

            } else {
                return "Error " + con.responseMessage
            }

        } catch (e: IOException){
            e.printStackTrace()
            return "Error" + e.message
        }
    }

    // show dialog while downloading data

    override fun onPreExecute() {
        super.onPreExecute()
        pd = ProgressDialog(c)

        if (status.equals("reg")) {
            pd.setTitle("Registering")
            pd.setMessage("Create new account..Please wait")
        } else if (status.equals("log")) {
            pd.setTitle("Log on")
            pd.setMessage("Connecting to your account..Please wait")
        }

        pd.show()

    }

    // perform background downloading of data
    override fun doInBackground(vararg voids: Void): String {
        return download()
    }

    // when background finishes, dismiss dialog and pass data to JSONParser
    override fun onPostExecute(jsonData: String) {
        super.onPostExecute(jsonData)

        pd.dismiss()
        if (jsonData.startsWith("URL ERROR")) {
            val error = jsonData
            Toast.makeText(c, error, Toast.LENGTH_SHORT).show()
            Toast.makeText(c, "MOST PROBABLY APP CANNOT CONNECT DUQ TO WRONG URL SINCE MALFORMEDURLEXCEPTION WAS RAISED", Toast.LENGTH_SHORT).show()

        }else if (jsonData.startsWith("CONNECT ERROR")) {
            val error = jsonData
            Toast.makeText(c, error, Toast.LENGTH_SHORT).show()
            Toast.makeText(c, "MOST PROBABLY APP CANNOT CONNECT TO ANY NETWORK SINCE IOEXCEPTION WAS RAISED", Toast.LENGTH_SHORT).show()
        }else {
            // parse
            //Toast.makeText(c, "Network Connection and Download Successful. Now attempting to parse .....", Toast.LENGTH_SHORT).show()
            //JSONParser(c, jsonData, myGridView).execute()
            Log_Req_Parser(c, jsonData, status).execute()
        }
    }
}