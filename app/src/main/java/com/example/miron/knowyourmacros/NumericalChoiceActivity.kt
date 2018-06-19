package com.example.miron.knowyourmacros

open class NumericalChoiceActivity : ChoiceActivity() {

    private lateinit var range: Range

    override fun initializeValuesFromIntent(){
        val intent = intent
        range = intent.extras.getSerializable("range") as Range
        pageTitle = intent.getStringExtra("name")
    }

    override fun addButtonsToMainLayout() {
        (range.min until range.max)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it) }
    }
}
