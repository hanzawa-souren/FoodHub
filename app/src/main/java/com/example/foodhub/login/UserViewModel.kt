package com.example.foodhub.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.tables.Donation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository

    private var readUser : User? = null

    private var userCount: LiveData<Int>

    private var liveUser: LiveData<User>? = null

    var loggedUser: LiveData<User> = MutableLiveData<User>(User(0, "", "","",0,0,0))


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

    fun getUser(id: String): LiveData<User>? {
        viewModelScope.launch(Dispatchers.IO) {
            liveUser = repository.getUser(id)
        }
        return liveUser
    }

    fun loginUser(id: String): User? {
        viewModelScope.launch(Dispatchers.IO) {
            readUser = repository.loginUser(id)
        }
        return readUser
    }

    fun getLogged(id: Int): LiveData<User> {
        viewModelScope.launch(Dispatchers.IO) {
            loggedUser = repository.getLogged(id)
        }
        return loggedUser
    }

    fun updateUsername(id: Int, logID : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateUsername(id, logID)
        }
    }

    fun updateUsernameCheck(id: String): User?{
        viewModelScope.launch(Dispatchers.IO){
            readUser = repository.updateUsernameCheck(id)
        }
        return readUser
    }

    fun updatePassword(id: Int, password : String){
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
    private lateinit var userDonation: LiveData<Double>
    fun getUserDonation(id: String): LiveData<Double>{

        userDonation = repository.getUserDonation(id)


        return userDonation
    }



}