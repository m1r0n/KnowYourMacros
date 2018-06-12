package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.os.Bundle

import android.preference.PreferenceFragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ListView


@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()
        setupActionBar()
    }

    class MyPreferenceFragment : PreferenceFragment() {
        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            if (view != null) {
                val listView = view.findViewById(android.R.id.list) as ListView
                val padding = resources.getDimension(R.dimen.fragment_padding).toInt()
                listView.setPadding(padding,0,padding,0)
            }
            return view
        }

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
