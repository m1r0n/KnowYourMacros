package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.os.Bundle
import android.preference.PreferenceActivity

import kotlinx.android.synthetic.main.activity_user_choice.*
import android.preference.PreferenceFragment



@SuppressLint("ExportedPreferenceActivity")
class UserChoiceActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, MyPreferenceFragment()).commit()

    }

    class MyPreferenceFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }
    }

}
