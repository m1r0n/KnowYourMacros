package com.example.miron.knowyourmacros

open class NumericalChoiceActivity : ChoiceActivity() {

    private lateinit var range: Range

    override fun initializeValuesFromIntent(){
        super.initializeValuesFromIntent()
        val intent = intent
        range = intent.extras.getSerializable("range") as Range
        pageTitle = intent.getStringExtra("name")
    }

    override fun addButtonsToMainLayout() {
        (range.min until range.max)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it) }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        scrollView.post {
            if (previousChoice != -1) {
                scrollView.scrollTo(0, Math.round((scrollView.getChildAt(0).height / (range.max - range.min) * (previousChoice - range.min - 4)).toFloat()))
            } else {
                scrollView.scrollTo(0, Math.round((scrollView.getChildAt(0).height / 2).toFloat()))
            }
        }
    }
}
