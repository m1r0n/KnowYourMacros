package com.example.miron.knowyourmacros

open class Values {
    companion object {

        //Gender
        private const val genderMale: String = "Male"
        private const val genderFemale: String = "Female"

        //Activity level
        private const val activityLevelSedentary: String = "Sedentary"
        private const val activityLevellight: String = "Lighty Active"
        private const val activityLevelModerate: String = "Moderately Active"
        private const val activityLevelVeryActive: String = "Very Active"
        private const val activityLevelExtreme: String = "Extremely Active"

        private const val activityLevelSedentaryDescription: String = "Little or no exercise"
        private const val activityLevellightDescription: String = "Exercise 1 to 3 days a week"
        private const val activityLevelModerateDescription: String = "Exercise 4 to 5 days a week"
        private const val activityLevelVeryActiveDescription: String = "Exercise 6 to 7 days a week"
        private const val activityLevelExtremeDescription: String = "Exercise every day, professional athlete"


        //Method
        private const val bmrHarrisBenedict: String = "Harris-Benedict"
        private const val bmrMifflin: String = "Mifflin St Jeor"
        private const val bmrKatch: String = "Katch-McArdle"
        private const val bmrKatchHybrid: String = "Katch-McArdle (Hybrid)"
        private const val bmrCunningham: String = "Cunningham"
        private const val fatProcentRequired: String = "Requires fat percentage"


        //Phase
        private const val phaseLosing: String = "Lose Weight"
        private const val phaseMaintaining: String = "Maintain Weight"
        private const val phaseGaining: String = "Gain Weight"
        private const val phaseLosingDescription: String = "Subtract 500 calories from TDEE"
        private const val phaseMaintainingDescription: String = "Basic TDEE"
        private const val phaseGainingDescription: String = "Add 500 calories to TDEE"

        //Diet type
        private const val dietLowCarb: String = "Lower Carbs"
        private const val dietModerateCarb: String = "Moderate Carbs"
        private const val dietHighCarb: String = "Higher Carbs"

        val genders: Array<String> = arrayOf(genderMale, genderFemale)
        val activityLevels: Array<String> = arrayOf(activityLevelSedentary, activityLevellight, activityLevelModerate, activityLevelVeryActive, activityLevelExtreme)
        val bmrTypes: Array<String> = arrayOf(bmrHarrisBenedict, bmrMifflin, bmrKatch, bmrKatchHybrid, bmrCunningham)
        val phases: Array<String> = arrayOf(phaseLosing, phaseMaintaining, phaseGaining)
        val dietTypes: Array<String> = arrayOf(dietLowCarb, dietModerateCarb, dietHighCarb)

        val activityLevelsDescription: Array<String> = arrayOf(activityLevelSedentaryDescription, activityLevellightDescription,
                activityLevelModerateDescription, activityLevelVeryActiveDescription, activityLevelExtremeDescription)
        val phasesDescription: Array<String> = arrayOf(phaseLosingDescription, phaseMaintainingDescription, phaseGainingDescription)
        val methodDescription: Array<String> = arrayOf("", "", fatProcentRequired, fatProcentRequired, fatProcentRequired)



        val compulsoryFields: Collection<String> = arrayOf("genderPreference", "activityPreference", "dietPreference", "agePreference", "heightPreference", "weightPreference", "phasePreference").toList()

        const val CompulsoryFieldsNotFilledError = "Please fill all compulsory fields!"

        const val api_url: String = "http://know-your-macros.herokuapp.com/macros"
    }
}