package com.example.miron.knowyourmacros

import android.os.AsyncTask
import android.util.Log
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection


class RequestMaker (basicURL: String, urlParameters: HashMap<String, Answer>): AsyncTask<String, Void, HashMap<String, Double>>() {

    private lateinit var url: URL

    init {
        try {
            Log.i("DATADATA", createURLFromParameters(basicURL, urlParameters))

            this.url = URL(createURLFromParameters(basicURL, urlParameters))
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    override fun doInBackground(vararg p0: String?): HashMap<String, Double> {
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
            return extractMacrosFromJSON(JSONObject(jsonString))
        } catch (e: Exception) {
            return HashMap()
        }

    }

    private fun extractMacrosFromJSON(jsonObject: JSONObject): HashMap<String, Double> {
        val result: HashMap<String, Double> = HashMap()
        result["calories"] = jsonObject.getDouble("calories")
        result["protein"] = jsonObject.getDouble("protein")
        result["carbs"] = jsonObject.getDouble("carbs")
        result["fats"] = jsonObject.getDouble("fats")
        return result
    }

    private fun createURLFromParameters(basicURL: String, urlParameters: HashMap<String, Answer>): String {
        val sb = StringBuilder(basicURL)
        sb.append("?")
        for ((key, value) in urlParameters) {
            sb.append(formatRequestParameterKey(key) + "=" + value.id + "&")
        }
        //urlParameters.forEach { k, v -> sb.append(formatRequestParameterKey(k) + "=" + v.id + "&")}
        //forEach and lambdas don't work on older versions of Android
        sb.setLength(sb.length-1)
        return sb.toString()
    }

    private fun formatRequestParameterKey(key: String): String {
        return key.dropLast(10)
    }

}