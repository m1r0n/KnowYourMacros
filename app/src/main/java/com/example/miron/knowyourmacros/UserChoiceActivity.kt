package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.app.Activity
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

        private val REQUEST_CODE = 200

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
                "agePreference" -> startAgePreferenceActivity()
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

        private fun startAgePreferenceActivity() {
            val intent = Intent(activity, NumericalChoiceActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        private fun startNominalActivity() {
            val intent = Intent(activity, NominalChoiceActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {

                    Log.i("DATADATA", (data.getIntExtra("User_Choice_Id", -1)).toString())

                }
            }
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
