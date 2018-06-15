package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.Preference

import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.util.Log
import android.view.*
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

        override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
            when(preference?.key) {
                "genderPreference" -> startNominalActivity()
                "agePreference" -> Log.i("DATADATA", "A")
                "activityLevelPreference" -> Log.i("DATADATA", "Ac")
                "heightPreference" ->Log.i("DATADATA", "H")
                "weightPreference" ->Log.i("DATADATA", "W")
                "fatPreference" ->Log.i("DATADATA", "Fat")
                "methodPreference" ->Log.i("DATADATA", "M")
                "phasePreference" ->Log.i("DATADATA", "Pha")
                "dietPreference" ->Log.i("DATADATA", "Die")
            }
            return super.onPreferenceTreeClick(preferenceScreen, preference)
        }

        fun startNominalActivity() {
            startActivity(Intent(activity, NominalChoiceActivity::class.java))
        }
    }

    @SuppressLint("InflateParams")
    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(true)
    }

    fun startNominalActivity(): Intent {
        val intent = Intent(this, NominalChoiceActivity::class.java)
        startActivity(intent)
        return intent
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    class IntentStarter {


        private var minValue: Int = 0
        private var maxValue: Int = 0
        private lateinit var options: Array<String>
        private lateinit var nameOfToolbarInActivity: String

        constructor(minValue: Int, maxValue: Int, nameOfToolbarInActivity: String) {
            this.minValue = minValue
            this.maxValue = maxValue
            this.nameOfToolbarInActivity = nameOfToolbarInActivity
        }

        constructor(options: Array<String>, nameOfToolbarInActivity: String) {
            this.options = options
            this.nameOfToolbarInActivity = nameOfToolbarInActivity
        }

    }

}
