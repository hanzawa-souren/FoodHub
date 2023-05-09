package com.example.foodhub.login

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.relations.UserWithDonations
import com.example.foodhub.database.tables.Donation

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun getUser(id: String): User

    @Insert
    suspend fun addDonation(donation: Donation)

    @Query("SELECT COUNT(loginID) FROM user_table")
    fun getUserCount(): LiveData<Int>
    @Query("SELECT SUM(d.d_Amount) FROM donation d INNER JOIN user_table u ON u.loginID = d.u_id WHERE u.loginID = :id")
    fun getUserDonation(id: String): LiveData<Int>
    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithDonations(): LiveData<List<UserWithDonations>>
}