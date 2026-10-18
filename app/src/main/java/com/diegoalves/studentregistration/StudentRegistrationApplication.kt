package com.diegoalves.studentregistration

import android.app.Application
import com.diegoalves.studentregistration.data.local.StudentDatabase
import com.diegoalves.studentregistration.data.repository.StudentRepository
import kotlin.getValue

class StudentRegistrationApplication : Application() {

    val database: StudentDatabase by lazy {
        StudentDatabase.getInstance(this)
    }

    val repository: StudentRepository by lazy {
        StudentRepository(database.studentDao())
    }
}