package com.example.pollapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {

    @Query("SELECT * FROM question")
    fun getAll(): List<Question>

    @Insert
    fun insertAll(vararg question: Question)

    @Delete
    fun delete(user: Question)
}