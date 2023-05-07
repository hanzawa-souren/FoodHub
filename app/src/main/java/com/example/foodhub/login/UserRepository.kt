package com.example.foodhub.login

import androidx.lifecycle.LiveData
import com.example.foodhub.database.tables.Donation

class UserRepository(private val userDao : UserDAO) {

    fun getUser(id: String): User {
        val readUser = userDao.getUser(id)
        return readUser
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUsername(id: String, logID : String){
        userDao.updateUser(id, logID)
    }

    suspend fun updatePassword(id: String, password : String){
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