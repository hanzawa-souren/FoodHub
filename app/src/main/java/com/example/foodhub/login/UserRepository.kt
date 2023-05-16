package com.example.foodhub.login

import androidx.lifecycle.LiveData
import com.example.foodhub.database.tables.Donation
import com.example.foodhub.database.tables.Helpline

class UserRepository(private val userDao : UserDAO) {

    fun getUser(id: String): LiveData<User> {
        val readUser = userDao.getUser(id)
        return readUser
    }

    fun loginUser(id: String): User? {
        val readUser = userDao.loginUser(id)
        return readUser
    }

    fun getLogged(id: Int): LiveData<User> {
        val readUser = userDao.getLogged(id)
        return readUser
    }
    fun updateDonationUser(oldID: String, newID : String){
        userDao.updateDonationUser(oldID, newID)
    }

    fun getUserDonation(id:String):LiveData<Double>{
        return userDao.getUserDonation(id)
    }

    fun getUserDateDay(id: String):LiveData<Int>{
        return userDao.getUserDateDay(id)
    }
    fun getUserDateMonth(id: String):LiveData<Int>{
        return userDao.getUserDateMonth(id)
    }
    fun getUserDateYear(id: String):LiveData<Int>{
        return userDao.getUserDateYear(id)
    }



    fun getDonationObject(id:String):LiveData<List<Donation>>{
        return userDao.getDonataionObject(id)
    }



    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUsername(id: Int, logID : String){
        userDao.updateUser(id, logID)
    }

    fun updateUsernameCheck(id: String): LiveData<User>{
        return userDao.updateUserCheck(id)
    }

    suspend fun updatePassword(id: Int, password : String){
        userDao.updatePassword(id, password)
    }

    suspend fun deleteUser(id: String){
        userDao.deleleUser(id)
    }

    suspend fun addDonation(donation: Donation) {
        userDao.addDonation(donation)
    }

    fun getUserCount(): LiveData<Int> {
        return userDao.getUserCount()
    }
}