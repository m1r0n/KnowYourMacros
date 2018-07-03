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
import kotlin.collections.HashMap



@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : AppCompatPreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //checkForPermission()
        setupFragmentManager()
        setupActionBar()
    }

    private fun setupFragmentManager() {
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()
    }

    @SuppressLint("InflateParams")
    private fun setupActionBar() {
        if (pageWasVisitedBefore()) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        else {
            supportActionBar?.setDisplayShowTitleEnabled(true)
        }
    }

    private fun pageWasVisitedBefore(): Boolean {
        val intent = intent
        return intent.getBooleanExtra("VisitingMarker", false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val userPreferences = MyPreferenceFragment.userPreferences
        val finalUserPreferences = MyPreferenceFragment.finalUserPreferences
        val doneButton = menu.findItem(R.id.preferences_done_button)

        doneButton.isEnabled = allUserPreferencesDataIsPresent(userPreferences) && userPreferences != finalUserPreferences
        if (doneButton.isEnabled) {
            doneButton.icon = resources.getDrawable(R.drawable.ic_done_white_24, null)
        }
        else {
            doneButton.icon = resources.getDrawable(R.drawable.ic_done_grey, null)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id) {
            android.R.id.home -> {
                val previousPreferences = MyPreferenceFragment.finalUserPreferences
                restoreUserPreferences(previousPreferences)
                finish()
            }

            R.id.preferences_done_button -> {
                val userPreferences = MyPreferenceFragment.userPreferences
                    storeLastUserPreferences(userPreferences)
                    showMacroSplitActivity(userPreferences)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreUserPreferences(previousPreferences: HashMap<String, Answer>) {
        MyPreferenceFragment.userPreferences.putAll(previousPreferences)
    }

    private fun storeLastUserPreferences(userPreferences: HashMap<String, Answer>) {
        MyPreferenceFragment.finalUserPreferences.putAll(userPreferences)
    }

    private fun showMacroSplitActivity(preferences: HashMap<String, Answer>) {
        val intent = Intent(this, NavigationActivity::class.java)
        intent.putExtra("preferences", preferences)
        startActivity(intent)
    }

    private fun allUserPreferencesDataIsPresent(userPreferences: HashMap<String, Answer>): Boolean {
        return userPreferences.keys.containsAll(Values.compulsoryFields)
    }


    class MyPreferenceFragment : PreferenceFragment() {

        companion object {
            var userPreferences: HashMap<String, Answer> = HashMap()
            var finalUserPreferences: HashMap<String, Answer> = HashMap()
            private val REQUEST_CODE = 200
            private lateinit var currentPreference: Preference
            private var currentPreferenceID: Int? = -1
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
            refreshFragmentView()
        }

        private fun refreshFragmentView() {
            val preferenceScreen = preferenceScreen
            val prefCount = preferenceScreen.preferenceCount

            for (i in 0 until prefCount) {
                val preference = preferenceScreen.getPreference(i)
                if (preference.key in finalUserPreferences) {
                    preference.widgetLayoutResource = R.layout.custom_checkbox_checked
                    preference.summary = finalUserPreferences[preference.key]!!.value
                }
            }
        }

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
            val view = super.onCreateView(inflater, container, savedInstanceState)
            addPaddingToView(resources.getDimension(R.dimen.fragment_padding).toInt())
            return view
        }

        private fun addPaddingToView(padding: Int) {
            if (view != null) {
                val listView = view.findViewById(android.R.id.list) as ListView
                listView.setPadding(padding,0,padding,0)
            }
        }

        override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
            currentPreferenceID = userPreferences[preference?.key]?.id
            when(preference?.key) {
                "genderPreference" -> askForNominalPreference(options = Values.genders, name = "Gender")
                "agePreference" -> askForNumericPreference(Range(10,70), "Age")
                "activityPreference" -> askForNominalPreference(Values.activityLevels, Values.activityLevelsDescription, "Activity Level")
                "heightPreference" -> askForNumericPreference(Range(130,220), "Height")
                "weightPreference" -> askForNumericPreference(Range(40,160), "Weight")
                "fatPreference" -> askForNumericPreference(Range(1,50), "Fat %")
                "methodPreference" -> askForMethodPreference(Values.bmrTypes, Values.methodDescription , "Method")
                "phasePreference" -> askForNominalPreference(Values.phases, Values.phasesDescription, "Phase")
                "dietPreference" -> askForNominalPreference(options = Values.dietTypes, name = "Diet")
            }
            currentPreference = preference!!
            return super.onPreferenceTreeClick(preferenceScreen, preference)
        }

        private fun askForMethodPreference(options: Array<String>, optionDescriptions: Array<String>, name: String) {
            val intent = Intent(activity, MethodChoiceActivity::class.java)
            intent.putExtra("fatSpecified", userPreferences.containsKey("fatPreference"))
            intent.putExtra("options", options)
            intent.putExtra("descriptions", optionDescriptions)
            intent.putExtra("name", name)
            intent.putExtra("previousID", currentPreferenceID)
            startActivityForResult(intent, REQUEST_CODE)
        }

        private fun askForNumericPreference(range: Range, name: String) {
            val intent = Intent(activity, NumericalChoiceActivity::class.java)
            intent.putExtra("range", range)
            intent.putExtra("name", name)
            intent.putExtra("previousID", currentPreferenceID)
            startActivityForResult(intent, REQUEST_CODE)
        }

        private fun askForNominalPreference(options: Array<String>, optionDescriptions: Array<String> = arrayOf(), name: String) {
            val intent = Intent(activity, NominalChoiceActivity::class.java)
            intent.putExtra("options", options)
            intent.putExtra("descriptions", optionDescriptions)
            intent.putExtra("name", name)
            intent.putExtra("previousID", currentPreferenceID)
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
                    updateDoneButton()
                }
            }
        }

        private fun updateUserPreferencesMap(value: Answer) {
            userPreferences[currentPreference.key] = value
        }

        private fun updateCurrentPreferenceSummary(userChoice: String) {
            currentPreference.summary = userChoice
        }

        private fun updateFragmentPage() {
            currentPreference.widgetLayoutResource = R.layout.custom_checkbox_checked
            fragmentManager.beginTransaction().detach(this).attach(this).commit()
        }

        private fun updateDoneButton() {
            activity.invalidateOptionsMenu()
        }

    }

}
