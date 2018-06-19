package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.preference.Preference

import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.*
import android.widget.ListView
import java.util.*


@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentManager()
        setupActionBar()
    }

    class MyPreferenceFragment : PreferenceFragment() {

        private val REQUEST_CODE = 200
        private var userPreferences: HashMap<String, Int> = HashMap()
        private lateinit var currentPreference: Preference

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            addPaddingToView(resources.getDimension(R.dimen.fragment_padding).toInt())
            return view
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }

        override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
            when(preference?.key) {
                "genderPreference" -> askForNominalPreference(Values.genders, "Gender")
                "agePreference" -> askForNumericPreference(Range(10,70), "Age")
                "activityLevelPreference" -> askForNominalPreference(Values.activityLevels, "Activity Level")
                "heightPreference" -> askForNumericPreference(Range(130,220), "Height")
                "weightPreference" -> askForNumericPreference(Range(40,160), "Weight")
                "fatPreference" -> askForNumericPreference(Range(1,50), "Fat %")
                "methodPreference" -> askForNominalPreference(Values.bmrTypes, "Method")
                "phasePreference" -> askForNominalPreference(Values.phases, "Phase")
                "dietPreference" -> askForNominalPreference(Values.dietTypes, "Diet")
            }
            currentPreference = preference!!
            return super.onPreferenceTreeClick(preferenceScreen, preference)
        }

        private fun askForNumericPreference(range: Range, name: String) {
            val intent = Intent(activity, NumericalChoiceActivity::class.java)
            intent.putExtra("range", range)
            intent.putExtra("name", name)
            startActivityForResult(intent, REQUEST_CODE)
        }

        private fun askForNominalPreference(options: Array<String>, name: String) {
            val intent = Intent(activity, NominalChoiceActivity::class.java)
            intent.putExtra("options", options)
            intent.putExtra("name", name)
            startActivityForResult(intent, REQUEST_CODE)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == REQUEST_CODE) {

                if (resultCode == Activity.RESULT_OK) {
                    val userChoice: Int = data.getIntExtra("User_Choice", 0)
                    updateUserPreferenceValue(userChoice)
                    updateCurrentPreference(userChoice)
                    updateFragmentPage()
                }
            }
        }

        private fun updateCurrentPreference(userChoice: Int) {
            currentPreference.summary = userChoice.toString()
            currentPreference.widgetLayoutResource = R.layout.custom_checkbox_checked
        }

        private fun updateFragmentPage() {
            fragmentManager.beginTransaction().detach(this).attach(this).commit()
        }

        private fun updateUserPreferenceValue(value: Int) {
            userPreferences[currentPreference.key] = value
        }

        private fun addPaddingToView(padding: Int) {
            if (view != null) {
                val listView = view.findViewById(android.R.id.list) as ListView
                listView.setPadding(padding,0,padding,0)
            }
        }
    }

    private fun setupFragmentManager() {
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()
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
