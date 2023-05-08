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

    suspend fun addDonation(donation: Donation) {
        userDao.addDonation(donation)
    }

    fun getUserCount(): LiveData<Int> {
        return userDao.getUserCount()
    }
}