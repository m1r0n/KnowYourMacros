package com.example.miron.knowyourmacros

import android.app.Activity
import android.content.Intent
import android.view.View

class NominalChoiceActivity : ChoiceActivity() {

    private lateinit var nominalOptions: Array<String>

    override fun initializeValuesFromIntent(){
        val intent = intent
        nominalOptions = intent.getStringArrayExtra("options")
        pageTitle = intent.getStringExtra("name")
    }

    override fun addButtonsToMainLayout() {
        (0 until nominalOptions.size)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it)
        }
    }

    override fun generateButtonText(buttonID: Int): CharSequence {
        return nominalOptions[buttonID]
    }

    override fun onClick(view: View) {
        val intent = Intent()
        val answer = Answer(view.id, nominalOptions[view.id])
        intent.putExtra("Answer", answer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
