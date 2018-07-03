package com.macrocalculator.miron.knowyourmacros

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.View

open class NominalChoiceActivity : ChoiceActivity() {

    private lateinit var nominalOptions: Array<String>
    private lateinit var nominalOptionsDescriptions: Array<String>

    override fun initializeValuesFromIntent(){
        super.initializeValuesFromIntent()
        val intent = intent
        nominalOptions = intent.getStringArrayExtra("options")
        nominalOptionsDescriptions = intent.getStringArrayExtra("descriptions")
        pageTitle = intent.getStringExtra("name")
    }

    override fun addButtonsToMainLayout() {
        (0 until nominalOptions.size)
                .map { createButtonWithID(it) }
                .forEach { mainLayout.addView(it)
        }
    }

    override fun generateButtonText(buttonID: Int): CharSequence {
        return if (nominalOptionsDescriptions.isEmpty() || nominalOptionsDescriptions[buttonID] == "") {
            nominalOptions[buttonID]
        }
        else {
            createHTMLString(buttonID)
        }

    }

    @SuppressWarnings("deprecation")
    private fun createHTMLString(buttonID: Int): Spanned {
        val color: String = String.format("#%06x", ContextCompat.getColor(this, R.color.summaryColor) and 0xffffff)
        val buttonText: String = nominalOptions[buttonID] + "<br/><small><font color=\"" + color + "\"> " + nominalOptionsDescriptions[buttonID] + "</font></small>"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(buttonText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(buttonText)
        }

    }

    override fun onClick(view: View) {
        val intent = Intent()
        val answer = Answer(view.id, nominalOptions[view.id])
        intent.putExtra("Answer", answer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun chooseButtonsTextSize(buttonID: Int): Float {
        return resources.getDimension(R.dimen.buttonTextSize)
    }
}
