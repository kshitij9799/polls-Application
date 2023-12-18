package com.example.pollapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Question(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "question") val question: String?,
    @ColumnInfo(name = "answer") val answer: String?,
    @ColumnInfo(name = "is_answed") val isAnswed: Boolean?,
    @ColumnInfo(name = "selected_option") val selectedOption: Int?
)
