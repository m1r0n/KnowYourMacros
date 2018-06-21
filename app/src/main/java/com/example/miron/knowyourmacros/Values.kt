package com.example.miron.knowyourmacros

open class Values {
    companion object {
        private const val genderMale: String = "Male"
        private const val genderFemale: String = "Female"

        private const val activityLevelSedentary: String = "Sedentary"
        private const val activityLevellight: String = "Lighty Active"
        private const val activityLevelModerate: String = "Moderately Active"
        private const val activityLevelVeryActive: String = "Very Active"
        private const val activityLevelExtreme: String = "Extremely Active"

        private const val bmrHarrisBenedict: String = "Harris-Benedict"
        private const val bmrMifflin: String = "Mifflin St Jeor"
        private const val bmrKatch: String = "Katch-McArdle"
        private const val bmrKatchHybrid: String = "Katch-McArdle (Hybrid)"
        private const val bmrCunningham: String = "Cunningham"

        private const val phaseLosing: String = "Lose Weight"
        private const val phaseMaintaining: String = "Maintain Weight"
        private const val phaseGaining: String = "Gain Weight"

        private const val dietLowCarb: String = "Lower Carbs"
        private const val dietModerateCarb: String = "Moderate Carbs"
        private const val dietHighCarb: String = "Higher Carbs"

        val genders: Array<String> = arrayOf(genderMale, genderFemale)
        val activityLevels: Array<String> = arrayOf(activityLevelSedentary, activityLevellight, activityLevelModerate, activityLevelVeryActive, activityLevelExtreme)
        val bmrTypes: Array<String> = arrayOf(bmrHarrisBenedict, bmrMifflin, bmrKatch, bmrKatchHybrid, bmrCunningham)
        val phases: Array<String> = arrayOf(phaseLosing, phaseMaintaining, phaseGaining)
        val dietTypes: Array<String> = arrayOf(dietLowCarb, dietModerateCarb, dietHighCarb)

        val compulsoryFields: Collection<String> = arrayOf("genderPreference", "activityLevelPreference", "dietPreference", "agePreference", "heightPreference", "weightPreference", "phasePreference").toList()
    }
}