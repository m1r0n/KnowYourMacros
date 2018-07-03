package com.macrocalculator.miron.knowyourmacros

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView

@SuppressLint("Registered")
open class ChoiceActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var scrollView: ScrollView
    lateinit var mainLayout: LinearLayout
    lateinit var pageTitle: String
    var previousChoice: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainLayout = LinearLayout(this)
        scrollView = ScrollView(this)

        initializeValuesFromIntent()
        addScrollViewBackGroundColor()
        setMainLayoutOrientationToVertical()
        addButtonsToMainLayout()
        setAppBarTitle()
        setupActionBar()

        scrollView.addView(mainLayout)
        setContentView(scrollView)
    }

    override fun onClick(view: View) {
        val intent = Intent()
        val answer = Answer(view.id, view.id.toString())
        intent.putExtra("Answer", answer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    private fun addScrollViewBackGroundColor() {
        scrollView.setBackgroundColor(resources.getColor(R.color.colorWindowBackground, this.theme))
    }

    private fun setMainLayoutOrientationToVertical() {
        mainLayout.orientation = LinearLayout.VERTICAL
    }

    open fun addButtonsToMainLayout() {
    }

    open fun initializeValuesFromIntent() {
        previousChoice = intent.getIntExtra("previousID", -1)
    }

    private fun setAppBarTitle() {
        title = pageTitle
    }

    fun createButtonWithID(buttonID: Int): Button {
        val button = layoutInflater.inflate(R.layout.userchoice_button_style, null) as Button
        button.id = buttonID
        button.setAllCaps(false)
        button.setOnClickListener(this)
        button.tag = buttonID
        button.text = generateButtonText(buttonID)
        button.isEnabled = buttonCanBeClicked(buttonID)
        button.setTextColor(chooseButtonColor(buttonID, button))
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, chooseButtonsTextSize(buttonID))

        return button
    }

    open fun chooseButtonsTextSize(buttonID: Int): Float {
        return if (buttonID == previousChoice) {
            resources.getDimension(R.dimen.buttonSelectedTextSize)
        } else {
            resources.getDimension(R.dimen.buttonTextSize)
        }
    }

    private fun chooseButtonColor(buttonID: Int, button: Button): Int {
        return if (button.isEnabled) {
            if (buttonID == previousChoice) {
                ResourcesCompat.getColor(resources, R.color.colorPrimaryDarkButton, null)
            } else {
                ResourcesCompat.getColor(resources, R.color.textColorPrimary, null)
            }
        } else {
            ResourcesCompat.getColor(resources, R.color.summaryColor, null)
        }
    }

    open fun buttonCanBeClicked(buttonID: Int): Boolean {
        return true
    }

    open fun generateButtonText(buttonID: Int): CharSequence {
        return buttonID.toString()
    }

    @SuppressLint("InflateParams")
    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent()
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
        return true
    }

}