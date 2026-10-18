package com.diegoalves.studentregistration.ui.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoalves.studentregistration.data.local.Student
import com.diegoalves.studentregistration.data.repository.StudentRepository
import com.diegoalves.studentregistration.validation.StudentValidator
import com.diegoalves.studentregistration.validation.YearValidationResult
import kotlinx.coroutines.launch

sealed class RegistrationResult {
    data class Success(val studentId: Int) : RegistrationResult()
    data class Failure(val message: String) : RegistrationResult()
}


class RegistrationViewModel(private val repository: StudentRepository) : ViewModel() {

    private val mutableRegistrationResult = MutableLiveData<RegistrationResult?>()

    fun registerStudent(name: String, course: String, yearText: String) {
        if (name.isBlank() || course.isBlank()) {
            mutableRegistrationResult.value = RegistrationResult.Failure("Name and course are required.")
            return
        }

        when (val yearValidation = StudentValidator.validateYear(yearText)) {
            is YearValidationResult.Invalid -> {
                mutableRegistrationResult.value = RegistrationResult.Failure(yearValidation.message)
            }
            is YearValidationResult.Valid -> {
                saveStudent(name.trim(), course.trim(), yearValidation.year)
            }
        }
    }

    val registrationResult: LiveData<RegistrationResult?> = mutableRegistrationResult

    private fun saveStudent(name: String, course: String, year: Int) {
        viewModelScope.launch {
            try {
                val newStudentId = repository.registerStudent(
                    Student(name = name, course = course, year = year)
                )
                mutableRegistrationResult.value = RegistrationResult.Success(newStudentId.toInt())
            } catch (databaseException: SQLiteException) {
                mutableRegistrationResult.value = RegistrationResult.Failure(
                    "Could not save the student. Please try again."
                )
            }
        }
    }
}