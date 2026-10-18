package com.diegoalves.studentregistration.validation

sealed class YearValidationResult {
    data class Valid(val year: Int) : YearValidationResult()
    data class Invalid(val message: String) : YearValidationResult()
}

object StudentValidator {

    const val MINIMUM_YEAR =1
    const val MAXIMUM_YEAR =4


    fun validateYear(yearText: String): YearValidationResult {
        val yearNumber = yearText.trim().toIntOrNull()

        if (yearNumber == null) {
            return YearValidationResult.Invalid("Year must be a number between 1 and 4.")
        }

        if (yearNumber < MINIMUM_YEAR || yearNumber > MAXIMUM_YEAR) {
            return YearValidationResult.Invalid("Year must be between 1 and 4.")
        }

        return YearValidationResult.Valid(yearNumber)
    }
}