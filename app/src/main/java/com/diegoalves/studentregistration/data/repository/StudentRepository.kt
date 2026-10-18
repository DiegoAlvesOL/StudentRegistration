package com.diegoalves.studentregistration.data.repository

import com.diegoalves.studentregistration.data.local.Student
import com.diegoalves.studentregistration.data.local.StudentDao


class StudentRepository(private val studentDao: StudentDao) {


    suspend fun registerStudent(student: Student): Long {
        return studentDao.insertStudent(student)
    }

    suspend fun findStudentById(studentId: Int): Student? {
        return studentDao.findStudentById(studentId)
    }
}