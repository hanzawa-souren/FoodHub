package com.example.foodhub.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val loginID : String,
    val password : String
)