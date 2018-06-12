package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.os.Bundle

import android.preference.PreferenceFragment
import android.view.Menu


@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()
        setupActionBar()
    }

    class MyPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }
    }

    @SuppressLint("InflateParams")
    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

}
