package com.diegoalves.studentregistration.ui.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegoalves.studentregistration.data.local.Student
import com.diegoalves.studentregistration.data.repository.StudentRepository
import kotlinx.coroutines.launch

sealed class SearchResult {

    data class Found(val student: Student) : SearchResult()


    data object NotFound : SearchResult()


    data class Failure(val message: String) : SearchResult()
}


class SearchViewModel(private val repository: StudentRepository) : ViewModel() {

    private val mutableSearchResult = MutableLiveData<SearchResult?>()


    val searchResult: LiveData<SearchResult?> = mutableSearchResult


    fun searchStudent(studentIdText: String) {
        val studentId = studentIdText.trim().toIntOrNull()

        if (studentId == null) {
            mutableSearchResult.value = SearchResult.Failure("Enter a valid numeric Student ID.")
            return
        }

        viewModelScope.launch {
            try {
                val foundStudent = repository.findStudentById(studentId)
                mutableSearchResult.value = if (foundStudent != null) {
                    SearchResult.Found(foundStudent)
                } else {
                    SearchResult.NotFound
                }
            } catch (databaseException: SQLiteException) {
                mutableSearchResult.value = SearchResult.Failure(
                    "Could not search right now. Please try again."
                )
            }
        }
    }
}