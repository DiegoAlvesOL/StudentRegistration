package com.diegoalves.studentregistration.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {

    @Insert
    suspend fun insertStudent(student: Student): Long


    @Query("SELECT * FROM students WHERE studentId = :studentId")
    suspend fun findStudentById(studentId: Int): Student?
}