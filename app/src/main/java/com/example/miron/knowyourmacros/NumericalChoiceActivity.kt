package com.example.miron.knowyourmacros

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView

class NumericalChoiceActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var scrollView: ScrollView
    private var minValue: Int = 10
    private var maxValue: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        scrollView = ScrollView(this)
        scrollView.setBackgroundColor(resources.getColor(R.color.colorWindowBackground, this.theme))

        val mainLayout = LinearLayout(this)
        mainLayout.orientation = LinearLayout.VERTICAL


        (minValue until maxValue)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it) }

        scrollView.addView(mainLayout)
        setContentView(scrollView)
        title = "SOMETHING"
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

    private fun createButtonWithID(buttonID: Int): Button {
        val button = Button(this)
        button.id = buttonID
        button.setAllCaps(false)
        button.setOnClickListener(this)
        button.tag = buttonID
        button.text = buttonID.toString()
        return button
    }
}
