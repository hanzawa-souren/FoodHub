package com.example.foodhub.login


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodhub.database.relations.UserWithDonations
import com.example.foodhub.database.tables.Donation

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("UPDATE user_table SET loginID = :logID WHERE id = :id")
    fun updateUser(id: Int, logID : String)

    @Query("UPDATE Donation SET u_id = :newID WHERE u_id = :oldID")
    fun updateDonationUser(oldID: String, newID : String)

    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun updateUserCheck(id: String): LiveData<User>
    @Query("UPDATE user_table SET password = :password WHERE id = :id")
    fun updatePassword(id: Int, password : String)

    @Query("DELETE FROM user_table WHERE loginID = :id")
    fun deleleUser(id: String)

    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun getUser(id: String): LiveData<User>

    @Query("SELECT * FROM user_table WHERE loginID = :id")
    fun loginUser(id: String): User?

    @Query("SELECT * FROM user_table WHERE id = :id")
    fun getLogged(id: Int): LiveData<User>

    @Insert
    suspend fun addDonation(donation: Donation)

    @Query("SELECT COUNT(loginID) FROM user_table")
    fun getUserCount(): LiveData<Int>
    @Query("SELECT SUM(d.d_Amount) FROM donation d INNER JOIN user_table u ON u.loginID = d.u_id WHERE u.loginID = :id")
    fun getUserDonation(id: String): LiveData<Double>
    @Query("SELECT d.day FROM donation d INNER JOIN user_table u ON u.loginID = d.u_id WHERE u.loginID = :id")
    fun getUserDonationDay(id: String): List<Int>

    @Query("SELECT d.month FROM donation d INNER JOIN user_table u ON u.loginID = d.u_id WHERE u.loginID = :id")
    fun getUserDonationMonth(id: String): List<Int>

    @Query("SELECT d.year FROM donation d INNER JOIN user_table u ON u.loginID = d.u_id WHERE u.loginID = :id")
    fun getUserDonationYear(id: String): List<Int>


    @Query("SELECT day FROM user_table WHERE loginID = :id")
    fun getUserDateDay(id: String): LiveData<Int>

    @Query("SELECT month FROM user_table WHERE loginID = :id")
    fun getUserDateMonth(id: String): LiveData<Int>
    @Query("SELECT year FROM user_table WHERE loginID = :id")
    fun getUserDateYear(id: String): LiveData<Int>

    @Query("SELECT * FROM donation WHERE u_id = :id")
    fun getDonataionObject(id:String): LiveData<List<Donation>>

    @Query("SELECT * FROM user_table ")
    fun getAllUser(): LiveData<List<User>>

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithDonations(): LiveData<List<UserWithDonations>>

}