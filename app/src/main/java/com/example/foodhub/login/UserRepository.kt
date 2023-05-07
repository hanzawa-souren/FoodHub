package com.example.foodhub.login

class UserRepository(private val userDao : UserDAO) {

    fun getUser(id: String): User {
        val readUser = userDao.getUser(id)
        return readUser
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

}