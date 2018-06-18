package com.example.miron.knowyourmacros

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView

open class NumericalChoiceActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var scrollView: ScrollView
    private lateinit var mainLayout: LinearLayout
    private var minValue: Int = 10
    private var maxValue: Int = 50
    private val pageTitle: String = "Age"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainLayout = LinearLayout(this)
        scrollView = ScrollView(this)
        title = pageTitle

        addScrollViewBackGroundColor()
        setMainLayoutOrientationToVertical()
        addButtonsToMainLayout()

        scrollView.addView(mainLayout)
        setContentView(scrollView)
    }

    override fun onClick(view: View) {
        val intent = Intent()
        intent.putExtra("User_Choice_Id", view.id)
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

    private fun addButtonsToMainLayout() {
        (minValue until maxValue)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it) }
    }

    private fun createButtonWithID(buttonID: Int): Button {
        val button = layoutInflater.inflate(R.layout.userchoice_button_style, null) as Button
        button.id = buttonID
        button.setAllCaps(false)
        button.setOnClickListener(this)
        button.tag = buttonID
        button.text = buttonID.toString()

        return button
    }
}
