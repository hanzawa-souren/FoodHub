package com.example.foodhub.user.login

class UserRepository(private val userDao : UserDAO) {

    suspend fun readUser(id: String): User {
        val read = userDao.getUser(id)
        return read
    }

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

}