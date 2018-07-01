package com.example.miron.knowyourmacros

class MethodChoiceActivity: NominalChoiceActivity() {

    private var fatSpecified: Boolean = false
    private val bmrKatch: Int = 2
    private val bmrKatchHybrid: Int = 3
    private val bmrCunningham: Int = 4

    override fun initializeValuesFromIntent(){
        super.initializeValuesFromIntent()
        fatSpecified = intent.getBooleanExtra("fatSpecified", false)
    }

    override fun buttonCanBeClicked(buttonID: Int): Boolean {
        return !(!fatSpecified && (buttonID == bmrKatch || buttonID == bmrKatchHybrid || buttonID == bmrCunningham))
    }


}