package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.preference.Preference

import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.util.Log
import android.view.*
import android.widget.ListView
import android.widget.Toast
import com.example.miron.knowyourmacros.Values.Companion.api_url
import org.json.JSONObject
import kotlin.collections.HashMap


@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupFragmentManager()
        setupActionBar()
    }

    class MyPreferenceFragment : PreferenceFragment() {

        private val REQUEST_CODE = 200
        private lateinit var currentPreference: Preference
        companion object {
            var userPreferences: HashMap<String, Answer> = HashMap()
        }

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
                "activityPreference" -> askForNominalPreference(Values.activityLevels, "Activity Level")
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
                    val userChoice: Answer = data.getSerializableExtra("Answer") as Answer
                    updateUserPreferencesMap(userChoice)
                    updateCurrentPreferenceSummary(userChoice.value)
                    updateFragmentPage()
                }
            }
        }

        private fun updateCurrentPreferenceSummary(userChoice: String) {
            currentPreference.summary = userChoice
        }

        private fun updateFragmentPage() {
            currentPreference.widgetLayoutResource = R.layout.custom_checkbox_checked
            fragmentManager.beginTransaction().detach(this).attach(this).commit()
        }

        private fun updateUserPreferencesMap(value: Answer) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId


        if (id == R.id.preferences_done_button) {
            val userPreferences = MyPreferenceFragment.userPreferences
            if(allUserPreferencesDataIsPresent(userPreferences)) {
                val requestMaker = RequestMaker(api_url, userPreferences)
                val jsonResponse: JSONObject = requestMaker.execute("").get()
                Log.i("DATADATA", jsonResponse.toString())
            }
            else {
                showErrorToast()
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showErrorToast() {
        val context = applicationContext
        val text = Values.CompulsoryFieldsNotFilledError
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    private fun allUserPreferencesDataIsPresent(userPreferences: HashMap<String, Answer>): Boolean {
        return userPreferences.keys.containsAll(Values.compulsoryFields)
    }

}
