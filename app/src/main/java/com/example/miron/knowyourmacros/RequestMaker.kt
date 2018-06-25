package com.example.miron.knowyourmacros

import android.os.AsyncTask
import android.util.Log
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap
import org.json.JSONObject
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection


class RequestMaker (basicURL: String, urlParameters: HashMap<String, Answer>): AsyncTask<String, Void, JSONObject>() {

    private lateinit var url: URL

    init {
        try {
            this.url = URL(createURLFromParameters(basicURL, urlParameters))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    override fun doInBackground(vararg p0: String?): JSONObject {
        try {
            val urlConnection: HttpURLConnection?
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            urlConnection.connectTimeout = 15000
            urlConnection.doOutput = true
            urlConnection.connect()

            val br = BufferedReader(InputStreamReader(url.openStream()))
            val sb = StringBuilder()

            var line : String?
            do {
                line = br.readLine()
                if (line == null)
                    break
                sb.append(line)
            } while (true)

            val jsonString = sb.toString()
            return JSONObject(jsonString)
        } catch (e: Exception) {
            return JSONObject("SOMETHING WENT WRONG")
        }

    }

    private fun createURLFromParameters(basicURL: String, urlParameters: HashMap<String, Answer>): String {
        val sb = StringBuilder(basicURL)
        sb.append("?")
        urlParameters.forEach { k, v -> sb.append(formatRequestParameterKey(k) + "=" + v.id + "&")}
        sb.setLength(sb.length-1)
        return sb.toString()
    }

    private fun formatRequestParameterKey(key: String): String {
        return key.dropLast(10)
    }

}