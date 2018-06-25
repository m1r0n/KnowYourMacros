package com.example.miron.knowyourmacros

import android.util.Log
import java.net.MalformedURLException
import java.net.URL
import java.util.HashMap

class RequestMaker (basicURL: String, urlParameters: HashMap<String, Answer>){
    private lateinit var url: URL

    init {
        val sb = StringBuilder(basicURL)
        sb.append("?")
        urlParameters.forEach { k, v -> sb.append(formatRequestParameterKey(k) + "=" + v.id + "&")}
        sb.setLength(sb.length-1)
        try {
            this.url = URL(sb.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        Log.i("DATADATA", url.toString())
    }

    private fun formatRequestParameterKey(key: String): String {
        return key.dropLast(10)
    }

}