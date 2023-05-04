package com.example.foodhub.user.login

import android.accessibilityservice.GestureDescription.StrokeDescription
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun getUser(id: String): User
}