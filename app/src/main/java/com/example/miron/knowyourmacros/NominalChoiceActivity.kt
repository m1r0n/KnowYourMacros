package com.example.miron.knowyourmacros

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
}
