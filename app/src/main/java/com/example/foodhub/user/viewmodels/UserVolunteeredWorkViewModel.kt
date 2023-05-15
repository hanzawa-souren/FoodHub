package com.example.foodhub.user.viewmodels

import com.example.foodhub.database.tables.UserVolunteeredWork
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.foodhub.database.AppDatabase
import com.example.foodhub.database.repositories.UserVolunteeredWorkRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserVolunteeredWorkViewModel(application: Application): AndroidViewModel(application) {
        private val repository : UserVolunteeredWorkRepo

    init {
        val userVolunteeredWorkDao = AppDatabase.getDatabase(application).userVolunteeredWorkDao()
        repository = UserVolunteeredWorkRepo(userVolunteeredWorkDao)

    }
    private lateinit var userVolunteeredWorkObject: LiveData<List<UserVolunteeredWork>>
    fun getEventsVolunteeredUser(id: String): LiveData<List<UserVolunteeredWork>> {
        userVolunteeredWorkObject = repository.getEventsVolunteeredUser(id)
        return userVolunteeredWorkObject

    }
    private lateinit var checkCount: LiveData<Int>
    fun checkVolunteered(id:String,vid:Int):LiveData<Int>{
        checkCount =  repository.checkVolunteered(id,vid)
        return checkCount
    }

    fun addVolunteeredWork(userVolunteeredWork: UserVolunteeredWork) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addVolunteeredWork(userVolunteeredWork)
        }
    }

    fun cancelEventsVolunteeredUser(uvwId: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.cancelEventsVolunteeredUser(uvwId)
        }
    }

    fun updateVolunteeredWorkStatus(uvwId:String,status:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateVolunteeredWorkStatus(uvwId,status)
        }
    }
    fun updateVolunteeredInfo(vId:Int,v_image:String,v_title:String,v_desc:String,v_street:String,v_city:String,v_postcode:String,v_state:String,v_country:String,v_website:String,v_phone:String,v_reg_link:String,v_maps:String,v_waze:String,day:Int,month:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateVolunteeredInfo(vId,v_image,v_title,v_desc,v_street,v_city,v_postcode,v_state,v_country,v_website,v_phone,v_reg_link,v_maps,v_waze,day,month)
        }
    }
    fun updateVWUserID(oldID: String, newID : String){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateVWUserID(oldID,newID)
        }
    }

}