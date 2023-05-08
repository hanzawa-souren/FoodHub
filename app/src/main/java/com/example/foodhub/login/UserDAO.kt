package com.example.foodhub.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.relations.UserWithDonations
import com.example.foodhub.database.tables.Donation

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("UPDATE user_table SET loginID = :logID WHERE loginID = :id")
    fun updateUser(id: String, logID : String)

    @Query("UPDATE user_table SET password = :password WHERE loginID = :id")
    fun updatePassword(id: String, password : String)

    @Query("DELETE FROM user_table WHERE loginID = :id")
    fun deleleUser(id: String)
    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun getUser(id: String): User?

    @Insert
    suspend fun addDonation(donation: Donation)

    @Query("SELECT COUNT(loginID) FROM user_table")
    fun getUserCount(): LiveData<Int>

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithDonations(): LiveData<List<UserWithDonations>>
}