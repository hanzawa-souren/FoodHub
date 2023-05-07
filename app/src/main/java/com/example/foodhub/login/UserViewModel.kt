package com.example.foodhub.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.tables.Donation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository
    private var readUser : User = User(0, "", "")
    private var userCount: LiveData<Int>

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        userCount = repository.getUserCount()
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun getUser(id: String): User {
        viewModelScope.launch(Dispatchers.IO) {
            readUser = repository.getUser(id)
        }
        return readUser
    }

    fun updateUsername(id: String, logID : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUsername(id, logID)
        }
    }

    fun updatePassword(id: String, password : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updatePassword(id, password)
        }
    }

    fun deleteUser(id: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteUser(id)
        }
    }

    fun addDonation(donation: Donation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDonation(donation)
        }
    }

    fun getUserCount(): LiveData<Int> {
        viewModelScope.launch(Dispatchers.IO) {
            userCount = repository.getUserCount()
        }
        return userCount
    }

}