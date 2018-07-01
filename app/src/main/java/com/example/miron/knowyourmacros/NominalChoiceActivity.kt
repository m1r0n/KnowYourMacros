package com.example.miron.knowyourmacros

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.view.View

class NominalChoiceActivity : ChoiceActivity() {

    private lateinit var nominalOptions: Array<String>
    private lateinit var nominalOptionsDescriptions: Array<String>

    override fun initializeValuesFromIntent(){
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
        return if (nominalOptionsDescriptions.isEmpty()) {
            nominalOptions[buttonID]
        }
        else {
            createHTMLString(buttonID)
        }

    }

    @SuppressWarnings("deprecation")
    private fun createHTMLString(buttonID: Int): Spanned {
        val buttonText: String = nominalOptions[buttonID] + "<br/><small><font color=\"#9d9b9a\"> " + nominalOptionsDescriptions[buttonID] + "</font></small>"
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(buttonText, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(buttonText);
        }

    }

    override fun onClick(view: View) {
        val intent = Intent()
        val answer = Answer(view.id, nominalOptions[view.id])
        intent.putExtra("Answer", answer)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
