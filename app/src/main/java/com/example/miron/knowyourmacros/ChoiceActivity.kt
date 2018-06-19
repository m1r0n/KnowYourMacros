package com.example.miron.knowyourmacros

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView

@SuppressLint("Registered")
open class ChoiceActivity: AppCompatActivity(), View.OnClickListener {

    lateinit var scrollView: ScrollView
    lateinit var mainLayout: LinearLayout
    lateinit var pageTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainLayout = LinearLayout(this)
        scrollView = ScrollView(this)

        initializeValuesFromIntent()
        addScrollViewBackGroundColor()
        setMainLayoutOrientationToVertical()
        addButtonsToMainLayout()
        setAppBarTitle()

        scrollView.addView(mainLayout)
        setContentView(scrollView)
    }

    override fun onClick(view: View) {
        val intent = Intent()
        intent.putExtra("User_Choice", view.id)
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

        return button
    }

    open fun generateButtonText(buttonID: Int): CharSequence {
        return buttonID.toString()
    }

}