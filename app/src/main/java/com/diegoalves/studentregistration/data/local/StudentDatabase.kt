package com.diegoalves.studentregistration.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var databaseInstance: StudentDatabase? = null


        fun getInstance(context: Context): StudentDatabase {
            return databaseInstance ?: synchronized(this) {
                databaseInstance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_registration.db"
                ).build().also { createdDatabase ->
                    databaseInstance = createdDatabase
                }
            }
        }
    }
}